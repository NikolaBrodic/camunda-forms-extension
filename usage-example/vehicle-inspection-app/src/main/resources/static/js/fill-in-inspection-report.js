const camundaUrl = 'http://localhost:8080/api/camunda';
const appointmentsUrl = 'http://localhost:8080/api/appointments';
const inspectionReportUrl = 'http://localhost:8080/api/inspection-reports';

const formId = 'formContainer';
const formEl = document.getElementById(formId);
const urlParams = new URLSearchParams(window.location.search);
const procInstanceId = urlParams.get('piId');
const taskId = urlParams.get('taskId');
const appointmentId = urlParams.get('appointment');

fetch(appointmentsUrl + `/${appointmentId}`)
    .then(response => response.json())
    .then(json => showAppointmentDetails(json));

fetch(camundaUrl + `/form-data?piId=${procInstanceId}&taskId=${taskId}`)
    .then(response => response.json())
    .then(json => generateFormFields(formId, json, setCssClasses(), true));

function showAppointmentDetails(appointment) {
    let table = document.createElement('table');

    let row1 = createTableRow('Date & time:', formatDateTime(appointment.dateTime));
    let row2 = createTableRow('Vehicle:', appointment.vehicle);
    let row3 = createTableRow('Client:', appointment.client.firstName + ' ' + appointment.client.lastName);
    let row4 = createTableRow('Phone:', appointment.client.phoneNum);
    table.append(row1, row2, row3, row4);

    document.getElementById('div-appointment').append(table);
}

let formatDateTime = (dateTime) => {
    let dtStr = new Date(dateTime).toLocaleString('sr-RS');
    let dtParts = dtStr.split(' ');
    let dt = dtParts[0] + ' at ' + dtParts[1];

    return dt;
}

formEl.onsubmit = (e) => {
    e.preventDefault();

    let formData = new FormData(formContainer);    
    mergeSelectedOptions(formData, 'fuelTypes');
    appendUncheckedCheckboxes(formData, formEl);
    convertDates(formData, formEl);

    let [fieldList, images] = convertToFieldList(formData);
    let imagesFormData = createFormData(images);

    fetch(camundaUrl + `/submit-form?piId=${procInstanceId}&taskId=${taskId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(fieldList)
    }).then(() => {
        return fetch(inspectionReportUrl + `/upload-images?piId=${procInstanceId}`, {
            method: 'POST',
            body: imagesFormData
        });
    }).then(() => {
        window.location.href = './appointments.html';
    });
}

let convertToFieldList = (formData) => {
    let images = formData.getAll('images');
    formData.delete('images');

    let fieldList = [];
    for (let [id, value] of formData) {
        fieldList.push({ id, value });
    }

    fieldList.push({
        id: 'images',
        value: 'Sent in separate request.'
    })

    return [fieldList, images];
}

let createFormData = (images) => {
    let formData = new FormData();
    images.forEach(image => formData.append('images', image));

    return formData;
}

let setCssClasses = () => {
    let css = new Map();

    css.set('lbl-vehicleType', 'form-label');
    css.set('vehicleType', 'form-select mb-2');
    css.set('lbl-vehicle', 'form-label');
    css.set('vehicle', 'form-control mb-2');
    css.set('lbl-productionYear', 'form-label');
    css.set('productionYear', 'form-control mb-2');
    css.set('lbl-engineCapacity', 'form-label');
    css.set('engineCapacity', 'form-control mb-2');
    css.set('lbl-enginePower', 'form-label');
    css.set('enginePower', 'form-control mb-3');
    css.set('lbl-fuelTypes', 'form-label');
    css.set('fuelTypes', 'form-select mb-2');
    css.set('div-hasNoMajorMechanicalDefects', 'form-check mt-3 mb-2');
    css.set('lbl-hasNoMajorMechanicalDefects', 'form-check-label lbl-space');
    css.set('hasNoMajorMechanicalDefects', 'form-check-input');
    css.set('div-hasNoMajorBodyDefects', 'form-check mb-2');
    css.set('lbl-hasNoMajorBodyDefects', 'form-check-label lbl-space');
    css.set('hasNoMajorBodyDefects', 'form-check-input');
    css.set('lbl-images', 'form-label');
    css.set('images', 'form-control mb-2');
    css.set('lbl-additionalNotes', 'form-label');
    css.set('additionalNotes', 'form-control mb-3');
    css.set('div-hasPassed', 'd-flex justify-content-center gray-box mb-2 py-2');
    css.set('div-hasPassed-true', 'd-inline-block has-passed-space');
    css.set('lbl-hasPassed-true', 'form-check-label lbl-space');
    css.set('hasPassed-true', 'form-check-input');
    css.set('div-hasPassed-false', 'd-inline-block');
    css.set('lbl-hasPassed-false', 'form-check-label lbl-space');
    css.set('hasPassed-false', 'form-check-input');
    css.set('lbl-inspectionDate', 'form-label');
    css.set('inspectionDate', 'form-control mb-2');
    css.set('lbl-inspectionTime', 'form-label');
    css.set('inspectionTime', 'form-control mb-2');

    return css;
}