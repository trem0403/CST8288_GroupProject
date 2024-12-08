document.addEventListener("DOMContentLoaded", () => {
    const institutionFilter = document.getElementById("institutionFilter");
    const termFilter = document.getElementById("termFilter");
    const searchBar = document.getElementById("searchBar");
    const searchButton = document.getElementById("searchButton");
    const courseTableBody = document.querySelector("#courseTable tbody");

    // Fetch initial courses (if needed for client-side rendering)
    let courses = []; // Populate this with AJAX or data injected into the page.

    // Filter and Search Logic
    const filterCourses = () => {
        const institution = institutionFilter.value.toLowerCase();
        const term = termFilter.value.toLowerCase();
        const query = searchBar.value.toLowerCase();

        const filteredCourses = courses.filter(course => {
            return (
                (!institution || course.institutionName.toLowerCase().includes(institution)) &&
                (!term || course.termName.toLowerCase().includes(term)) &&
                (course.title.toLowerCase().includes(query) || course.code.toLowerCase().includes(query))
            );
        });

        renderCourses(filteredCourses);
    };

    // Render Courses into Table
    const renderCourses = (courseList) => {
        courseTableBody.innerHTML = "";
        if (courseList.length === 0) {
            courseTableBody.innerHTML = `<tr><td colspan="10">No courses found.</td></tr>`;
            return;
        }

        courseList.forEach(course => {
            const row = `
                <tr>
                    <td>${course.institutionID}</td>
                    <td>${course.title}</td>
                    <td>${course.code}</td>
                    <td>${course.termID}</td>
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
                </tr>
            `;
            courseTableBody.insertAdjacentHTML("beforeend", row);
        });

        attachRequestToTeachListeners();
    };

    // Attach event listeners for Request to Teach buttons
    const attachRequestToTeachListeners = () => {
        document.querySelectorAll(".request-to-teach").forEach(button => {
            button.addEventListener("click", (event) => {
                const courseId = event.target.dataset.courseId;
                fetch("/RequestToTeachServlet", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: `courseID=${courseId}`
                })
                .then(response => response.text())
                .then(() => alert("Request to teach submitted successfully!"))
                .catch(error => console.error("Failed to submit request:", error));
            });
        });
    };

    // Attach filter and search event listeners
    searchButton.addEventListener("click", filterCourses);
    institutionFilter.addEventListener("change", filterCourses);
    termFilter.addEventListener("change", filterCourses);
});
