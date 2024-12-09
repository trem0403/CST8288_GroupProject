document.addEventListener("DOMContentLoaded", () => {
    // Get references to DOM elements
    const courseTableBody = document.querySelector("#courseTable tbody");
    const institutionFilter = document.getElementById("institutionFilter");
    const termFilter = document.getElementById("termFilter");
    const searchInput = document.getElementById("searchInput");

    // Function to filter and search courses based on user input
    function filterAndSearchCourses() {
        // Get the search query and selected filter values
        const searchQuery = searchInput.value.toLowerCase();
        const selectedInstitution = institutionFilter.value;
        const selectedTerm = termFilter.value;

        // Filter the courses based on selected filters and search query (Course title-specific)
        const filteredCourses = courses.filter(course => {
            // Check if the course matches the selected institution filter
            const matchesInstitution = selectedInstitution === "all" || course.institutionID.toString() === selectedInstitution;

            // Check if the course matches the selected term filter
            const matchesTerm = selectedTerm === "all" || course.termID.toString() === selectedTerm;

            // Check if the course title matches the search query
            const matchesSearch = course.title.toLowerCase().includes(searchQuery); // Search specific to title

            // Only include courses that match all selected filters and search query
            return matchesInstitution && matchesTerm && matchesSearch;
        });

        // Populate the table with filtered courses
        populateTable(filteredCourses);
    }

    // Function to populate the table with filtered course data
    function populateTable(filteredCourses) {
        // Clear existing rows in the table body
        courseTableBody.innerHTML = "";

        // If no courses match the filters, show a "No courses found" message
        if (!filteredCourses || filteredCourses.length === 0) {
            const noDataRow = document.createElement("tr");
            noDataRow.innerHTML = `<td colspan="10">No courses found.</td>`;
            courseTableBody.appendChild(noDataRow);
            return;
        }

        // Loop through the filtered courses and create table rows for each course
        filteredCourses.forEach(course => {
            const row = document.createElement("tr");

            // Create table cells with course data
            row.innerHTML = `
                <td>${institutionMap[course.institutionID] || "Unknown"}</td>
                <td>${course.title}</td>
                <td>${course.code}</td>
                <td>${termMap[course.termID] || "Unknown"}</td>
                <td>${course.outline}</td>
                <td>${course.schedule}</td>
                <td>${course.deliveryMethod}</td>
                <td>${course.preferredQualifications}</td>
                <td>${course.compensation}</td>
                <td>
                    <button class="request-to-teach" data-course-id="${course.courseID}">
                        Request to Teach
                    </button>
                </td>
            `;

            // Append the row to the table body
            courseTableBody.appendChild(row);
        });

        // Add event listeners for the "Request to Teach" buttons
        const requestButtons = document.querySelectorAll('.request-to-teach');
        requestButtons.forEach(button => {
            button.addEventListener('click', handleRequestToTeach);
        });
    }

    // Handle the "Request to Teach" button click
    function handleRequestToTeach(event) {
        const courseID = event.target.dataset.courseId;

        alert(`Request to teach for course ID ${courseID} submitted successfully!`);

         fetch('/request-to-teach', {
             method: 'POST',
             headers: {
                 'Content-Type': 'application/json'
             },
             body: JSON.stringify({ courseID })
         })
         .then(response => response.json())
         .then(data => {
             alert("Request successfully sent.");
         })
         .catch(error => {
             console.error("Error:", error);
         });

    }

    // Add event listeners to the filters and search input
    institutionFilter.addEventListener("change", filterAndSearchCourses); // Trigger filtering on institution change
    termFilter.addEventListener("change", filterAndSearchCourses); // Trigger filtering on term change
    searchInput.addEventListener("input", filterAndSearchCourses); // Trigger filtering on search input changes

    // Populate the table with all courses on page load
    populateTable(courses);
});
