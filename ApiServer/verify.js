import './server.js';

const PORT = process.env.PORT || 5000;
const BASE_URL = `http://localhost:${PORT}`;

// Helper delay function
const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

async function runTests() {
  console.log('\n--- Starting LMS API Automated Tests ---\n');
  await delay(1000); // Wait for Express server to start listening

  let jwtToken = '';
  let note1Id = '';
  let note2Id = '';

  try {
    // 1. Sanity check root path
    console.log('Test 1: GET / (Sanity Check)');
    const rootRes = await fetch(`${BASE_URL}/`);
    const rootData = await rootRes.json();
    console.log(`Status: ${rootRes.status}, Response:`, rootData);
    if (!rootData.success) throw new Error('Root route failed');

    // 2. Public institutions
    console.log('\nTest 2: GET /getAll/institution (Public API)');
    const instRes = await fetch(`${BASE_URL}/getAll/institution`);
    const instData = await instRes.json();
    console.log(`Status: ${instRes.status}, Count: ${instData.data.length}`);
    if (instData.data.length === 0) throw new Error('Should return seeded institutions');

    // 3. Protected Route - Unauthenticated access
    console.log('\nTest 3: GET /roles/getAll (Without Token - Expect 401)');
    const rolesNoAuthRes = await fetch(`${BASE_URL}/roles/getAll`);
    const rolesNoAuthData = await rolesNoAuthRes.json();
    console.log(`Status: ${rolesNoAuthRes.status}, Message: "${rolesNoAuthData.message}"`);
    if (rolesNoAuthRes.status !== 401) throw new Error('Expected 401 Unauthorized');

    // 4. Authenticate User
    console.log('\nTest 4: POST /user/login (Preloaded Admin User)');
    const loginRes = await fetch(`${BASE_URL}/user/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        email: 'sam@gmail.com',
        password: '123'
      })
    });
    const loginData = await loginRes.json();
    console.log(`Status: ${loginRes.status}, Success: ${loginData.success}`);
    if (!loginData.success || !loginData.data.token) {
      throw new Error('Authentication failed');
    }
    jwtToken = loginData.data.token;
    console.log(`Token received: ${jwtToken.substring(0, 25)}...`);

    // 5. Protected Route - Authenticated roles retrieval
    console.log('\nTest 5: GET /roles/getAll (With Token - Expect 200)');
    const rolesRes = await fetch(`${BASE_URL}/roles/getAll`, {
      headers: { Authorization: `Bearer ${jwtToken}` }
    });
    const rolesData = await rolesRes.json();
    console.log(`Status: ${rolesRes.status}, Roles count: ${rolesData.data.length}`);
    if (rolesRes.status !== 200 || rolesData.data.length === 0) {
      throw new Error('Could not retrieve roles using JWT');
    }

    // 6. Create Note 1
    console.log('\nTest 6: POST /create/notes (Create Note 1)');
    const createNote1Res = await fetch(`${BASE_URL}/create/notes`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${jwtToken}`
      },
      body: JSON.stringify({
        title: 'Automation Testing Guide',
        content: 'Learn to use Rest Assured and Postman to test APIs.',
        tags: ['education', 'testing'],
        color: 'blue'
      })
    });
    const note1Data = await createNote1Res.json();
    console.log(`Status: ${createNote1Res.status}, Title: "${note1Data.data.title}"`);
    if (createNote1Res.status !== 201) throw new Error('Note creation failed');
    note1Id = note1Data.data.id;
    console.log(`Note 1 ID: ${note1Id}`);

    // 7. Create Note 2
    console.log('\nTest 7: POST /create/notes (Create Note 2)');
    const createNote2Res = await fetch(`${BASE_URL}/create/notes`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${jwtToken}`
      },
      body: JSON.stringify({
        title: 'Weekly Task List',
        content: 'Write implementation plan, test routes, commit to git.',
        tags: ['tasks', 'work'],
        isPinned: true
      })
    });
    const note2Data = await createNote2Res.json();
    console.log(`Status: ${createNote2Res.status}, Title: "${note2Data.data.title}"`);
    note2Id = note2Data.data.id;
    console.log(`Note 2 ID: ${note2Id}`);

    // 8. Get All Notes (Pagination & filters)
    console.log('\nTest 8: GET /getAll/notes (Search and tag filter)');
    const searchRes = await fetch(`${BASE_URL}/getAll/notes?search=Testing&tags=education`, {
      headers: { Authorization: `Bearer ${jwtToken}` }
    });
    const searchData = await searchRes.json();
    console.log(`Status: ${searchRes.status}, Match count: ${searchData.data.notes.length}`);
    if (searchData.data.notes.length !== 1) throw new Error('Search filter did not match expected note');

    // 9. Update Note
    console.log('\nTest 9: PUT /update/notes/:id (Update Note 1 title)');
    const updateRes = await fetch(`${BASE_URL}/update/notes/${note1Id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${jwtToken}`
      },
      body: JSON.stringify({
        title: 'Updated Automation Guide',
        color: 'emerald'
      })
    });
    const updateData = await updateRes.json();
    console.log(`Status: ${updateRes.status}, New Title: "${updateData.data.title}"`);
    if (updateData.data.title !== 'Updated Automation Guide') {
      throw new Error('Title update was not applied');
    }

    // 10. Toggle Pin Note
    console.log('\nTest 10: PUT /toggle-pin/notes/:id (Toggle pin on Note 1)');
    const toggleRes = await fetch(`${BASE_URL}/toggle-pin/notes/${note1Id}`, {
      method: 'PUT',
      headers: { Authorization: `Bearer ${jwtToken}` }
    });
    const toggleData = await toggleRes.json();
    console.log(`Status: ${toggleRes.status}, isPinned: ${toggleData.data.isPinned}`);
    if (!toggleData.data.isPinned) throw new Error('Pin toggle failed');

    // 11. Delete Notes Bulk
    console.log(`\nTest 11: DELETE /delete/notes/ById/:id* (Bulk delete: ${note1Id} and ${note2Id})`);
    const deleteRes = await fetch(`${BASE_URL}/delete/notes/ById/${note1Id}/${note2Id}`, {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${jwtToken}` }
    });
    const deleteData = await deleteRes.json();
    console.log(`Status: ${deleteRes.status}, Deleted Count: ${deleteData.data.deletedCount}`);
    if (deleteData.data.deletedCount !== 2) throw new Error('Bulk delete failed');

    // 12. Verify Notes are empty
    console.log('\nTest 12: GET /getAll/notes (Check if notes list is empty)');
    const finalGetRes = await fetch(`${BASE_URL}/getAll/notes`, {
      headers: { Authorization: `Bearer ${jwtToken}` }
    });
    const finalGetData = await finalGetRes.json();
    console.log(`Status: ${finalGetRes.status}, Notes count: ${finalGetData.data.notes.length}`);
    if (finalGetData.data.notes.length !== 0) throw new Error('Notes were not fully deleted');

    console.log('\n✅ ALL TESTS PASSED SUCCESSFULLY!\n');
    process.exit(0);
  } catch (error) {
    console.error('\n❌ TEST SUITE FAILED:', error.message);
    process.exit(1);
  }
}

runTests();
