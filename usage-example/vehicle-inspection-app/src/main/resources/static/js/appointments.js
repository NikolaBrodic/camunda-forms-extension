const appointmentsUrl = 'http://localhost:8080/api/appointments';

document.getElementById('date-span').innerText = new Date().toLocaleDateString('sr-RS');

fetch(appointmentsUrl)
    .then(response => response.json())
    .then(json => {
        if (Array.isArray(json) && json.length) {
            showTasks(json);
        } else {
            showMessageBox();
        }
    });

function showTasks(taskList) {
    let fragment = new DocumentFragment();

    for (let task of taskList) {
        let div = document.createElement('div');
        div.id = 'apt-' + task.appointment.id;
        div.classList.add('d-flex', 'justify-content-between', 'align-items-center', 
            'gray-box', 'px-3', 'py-2', 'my-3');

        let timeDiv = document.createElement('div');
        let dtStr = new Date(task.appointment.dateTime).toLocaleTimeString('sr-RS');
        timeDiv.innerText = dtStr.substring(0, 5);
        timeDiv.classList.add('inspection-time');

        let tableDiv = document.createElement('div');
        tableDiv.classList.add('w-table')

        let table = document.createElement('table');
        let row1 = createTableRow('Vehicle:', task.appointment.vehicle);
        let row2 = createTableRow('Client:', task.appointment.client.firstName + ' ' + task.appointment.client.lastName);
        let row3 = createTableRow('Phone:', task.appointment.client.phoneNum);
        table.append(row1, row2, row3);

        tableDiv.append(table);

        let chooseBtn = document.createElement('button');
        chooseBtn.innerText = 'Inspect';
        chooseBtn.onclick = showInspectionPage;
        chooseBtn.dataset.appointment = task.appointment.id;
        chooseBtn.dataset.piId = task.execution.procInstanceId;
        chooseBtn.dataset.taskId = task.execution.taskId;
        chooseBtn.classList.add('btn', 'btn-primary');
        
        div.append(timeDiv, tableDiv, chooseBtn);
        fragment.append(div);
    }

    document.getElementById('div-appointments').append(fragment);
}

function showInspectionPage() {
    let clickedBtn = this;
    window.location.href = 'fill-in-inspection-report.html'
        + '?appointment=' + clickedBtn.dataset.appointment
        + '&piId=' + clickedBtn.dataset.piId
        + '&taskId=' + clickedBtn.dataset.taskId;
}

function showMessageBox() {
    let msgBox = document.createElement('div');
    msgBox.innerText = 'No vehicles waiting for inspection.';
    msgBox.classList.add('gray-box', 'fst-italic', 'text-center', 'px-3', 'py-3', 'mt-4');

    document.getElementById('div-appointments').append(msgBox);
}