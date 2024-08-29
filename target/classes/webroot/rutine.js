document.getElementById('exerciseForm').addEventListener('submit', loadPostMsg);

function loadPostMsg(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const series = document.getElementById('series').value;
    let url = `/app/addExercise?name=${encodeURIComponent(name)}&series=${encodeURIComponent(series)}`;

    fetch(url, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('message').innerText = `Ejercicio: ${data.name} añadido correctamente`;
            fetchExercises();
        })
        .catch(error => {
            console.error("Error al añadir ejercicio:", error);
            document.getElementById('message').innerText = 'Error al añadir ejercicio';
        }); 
}


function fetchExercises() {
    fetch('/app/exercises')
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log("Data received:", data);
            let exerciseItems = document.getElementById('exerciseItems');
            exerciseItems.innerHTML = '';
            data.exercises.forEach(exercise => {
                let li = document.createElement('li');
                li.textContent = `Ejercicio: ${exercise.name}, #Series: ${exercise.series}`;
                exerciseItems.appendChild(li);
            });
        })
        .catch(error => {
            console.error('Error al cargar los ejercicios:', error);
            document.getElementById('message').innerText = 'Error al cargar los ejercicios.';
        });
}

window.onload = fetchExercises();