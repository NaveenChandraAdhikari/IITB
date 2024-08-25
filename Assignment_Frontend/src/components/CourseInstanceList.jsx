import React, { useState } from 'react';
import InstanceDetailsModal from './InstanceDetailsModal'; // Make sure to create this component

function CourseInstanceList() {
  const [instances, setInstances] = useState([]);
  const [year, setYear] = useState('');
  const [semester, setSemester] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [selectedInstance, setSelectedInstance] = useState(null);

  const fetchInstances = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/instances/${year}/${semester}?page=${page}&size=10`);
      const data = await response.json();
      console.log('Fetched instances:', data.content);
      setInstances(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error('Error fetching instances:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/instances/${year}/${semester}/${id}`, { method: 'DELETE' });
      if (response.ok) {
        fetchInstances();
      } else {
        throw new Error('Failed to delete course instance');
      }
    } catch (error) {
      console.error('Error deleting course instance:', error);
      alert('Failed to delete course instance');
    }
  };

  const viewInstanceDetails = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/instances/${year}/${semester}/${id}`);
      const data = await response.json();
      console.log('Instance details:', data);
      setSelectedInstance(data);
    } catch (error) {
      console.error('Error fetching instance details:', error);
    }
  };

  const closeModal = () => {
    setSelectedInstance(null);
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Year"
        value={year}
        onChange={(e) => setYear(e.target.value)}
      />
      <select
        value={semester}
        onChange={(e) => setSemester(e.target.value)}
      >
        <option value="">Select semester</option>
        <option value="1">1</option>
        <option value="2">2</option>
      </select>
      <button onClick={fetchInstances}>List instances</button>
      <table style={{width: '100%', borderCollapse: 'collapse'}}>
        <thead>
          <tr style={{backgroundColor: '#4285F4', color: 'white'}}>
            <th style={{padding: '10px'}}>Course Title</th>
            <th style={{padding: '10px'}}>Year-Sem</th>
            <th style={{padding: '10px'}}>Code</th>
            <th style={{padding: '10px'}}>Action</th>
          </tr>
        </thead>
        <tbody>
          {instances.map((instance, index) => (
            <tr key={instance.id} style={{backgroundColor: index % 2 === 0 ? '#E8F0FE' : 'white'}}>
              <td style={{padding: '10px'}}>{instance.courseTitle}</td>
              <td style={{padding: '10px'}}>{`${instance.year}-${instance.semester}`}</td>
              <td style={{padding: '10px'}}>{instance.courseCode}</td>
              <td style={{padding: '10px'}}>
                <button onClick={() => viewInstanceDetails(instance.id)} style={{marginRight: '5px'}}>🔍</button>
                <button onClick={() => handleDelete(instance.id)}>🗑️</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div style={{marginTop: '20px'}}>
        <button onClick={() => setPage(prev => Math.max(prev - 1, 0))} disabled={page === 0}>
          Previous
        </button>
        <span style={{margin: '0 10px'}}>Page {page + 1} of {totalPages}</span>
        <button onClick={() => setPage(prev => Math.min(prev + 1, totalPages - 1))} disabled={page === totalPages - 1}>
          Next
        </button>
      </div>
      
      {selectedInstance && (
        <InstanceDetailsModal instance={selectedInstance} onClose={closeModal} />
      )}
    </div>
  );
}

export default CourseInstanceList;


// import React, { useState } from 'react';

// function CourseInstanceList() {
//     const [instances, setInstances] = useState([]);
//     const [year, setYear] = useState('');
//     const [semester, setSemester] = useState('');
//     const [page, setPage] = useState(0);
//     const [totalPages, setTotalPages] = useState(0);
  
//     const fetchInstances = async () => {
//       try {
//         const response = await fetch(`http://localhost:8080/api/instances/${year}/${semester}?page=${page}&size=10`);
//         const data = await response.json();
//         console.log('Fetched instances:', data.content); // Log the entire array
//         setInstances(data.content);
//         setTotalPages(data.totalPages);
//       } catch (error) {
//         console.error('Error fetching instances:', error);
//       }
//     };
  
//     const handleDelete = async (id) => {
//       try {
//         const response = await fetch(`http://localhost:8080/api/instances/${year}/${semester}/${id}`, { method: 'DELETE' });
//         if (response.ok) {
//           fetchInstances();
//         } else {
//           throw new Error('Failed to delete course instance');
//         }
//       } catch (error) {
//         console.error('Error deleting course instance:', error);
//         alert('Failed to delete course instance');
//       }
//     };
  
//     const viewInstanceDetails = async (id) => {
//       try {
//         const response = await fetch(`http://localhost:8080/api/instances/${year}/${semester}/${id}`);
//         const data = await response.json();
//         // Display the instance details (you might want to use a modal or a separate component for this)
//         console.log('Instance details:', data);
//       } catch (error) {
//         console.error('Error fetching instance details:', error);
//       }
//     };
 

//   return (
//     <div>
//       <input
//         type="text"
//         placeholder="Year"
//         value={year}
//         onChange={(e) => setYear(e.target.value)}
//       />
//       <select
//         value={semester}
//         onChange={(e) => setSemester(e.target.value)}
//       >
//         <option value="">Select semester</option>
//         <option value="1">1</option>
//         <option value="2">2</option>
//       </select>
//       <button onClick={fetchInstances}>List instances</button>
//       <table>
//         <thead>
//           <tr>
//             <th>Course Title</th>
//             <th>Year-Sem</th>
//             <th>Code</th>
//             <th>Action</th>
//           </tr>
//         </thead>
//         <tbody>
//           {instances.map(instance => (
//             <tr key={instance.id}>
//               <td>{instance.course.title}</td>
//               <td>{`${instance.year}-${instance.semester}`}</td>
//               <td>{instance.course.code}</td>
//               <td>
//               <button onClick={() => viewInstanceDetails(instance.id)}>View</button>
//                 <button onClick={() => handleDelete(instance.id)}>Delete</button>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//       <div>
//         <button onClick={() => setPage(prev => Math.max(prev - 1, 0))} disabled={page === 0}>
//           Previous
//         </button>
//         <span>Page {page + 1} of {totalPages}</span>
//         <button onClick={() => setPage(prev => Math.min(prev + 1, totalPages - 1))} disabled={page === totalPages - 1}>
//           Next
//         </button>
//       </div>
//     </div>
//   );
// }

// export default CourseInstanceList;