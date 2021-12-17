const inspectionReportUrl = 'http://localhost:8080/api/inspection-reports';

fetch(inspectionReportUrl)
    .then(response => response.json())
    .then(json => {
        if (Array.isArray(json) && json.length) {
            showInspectionReports(json);
        } else {
            showMessageBox();
        }
    });

function showInspectionReports(reports) {
    let fragment = new DocumentFragment();

    for (let report of reports) {
        let div = document.createElement('div');
        div.id = 'report-' + report.id;
        div.classList.add('gray-box', 'px-3', 'py-2', 'my-3');

        let table = document.createElement('table');
        let row1 = createTableRow('Inspected on:', formatDateAndTime(report.inspectionDate, report.inspectionTime));
        let row2 = createTableRow('Vehicle:', report.vehicle);
        let row3 = createTableRow('Production year:', report.productionYear + '.');
        let row4 = createTableRow('Results:', report.hasPassed ? 'Passed' : 'Failed');
        row4.lastElementChild.classList.add(report.hasPassed ? 'passed' : 'not-passed');
        table.append(row1, row2, row3, row4);

        div.append(table);
        fragment.append(div);
    }

    document.getElementById('div-reports').append(fragment);
}

let formatDateAndTime = (date, time) => {
    let dateStr = new Date(date).toLocaleDateString('sr-RS');
    let dateTime = dateStr + ' at ' + time.substring(0, 5);

    return dateTime;
}

function showMessageBox() {
    let msgBox = document.createElement('div');
    msgBox.innerText = 'Inspection report history is empty at the moment.';
    msgBox.classList.add('gray-box', 'fst-italic', 'text-center', 'px-3', 'py-3', 'mt-4');

    document.getElementById('div-reports').append(msgBox);
}