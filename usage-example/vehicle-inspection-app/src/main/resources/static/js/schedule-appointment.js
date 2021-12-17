const camundaUrl = 'http://localhost:8080/api/camunda';

const formId = 'formContainer';
const formEl = document.getElementById(formId);
let procInstanceId, taskId;

fetch(camundaUrl + '/start-process')
    .then(response => response.json())
    .then(json => {
        procInstanceId = json.procInstanceId;
        taskId = json.taskId;
        getFormData();
    });

function getFormData() {
    fetch(camundaUrl + `/form-data?piId=${procInstanceId}&taskId=${taskId}`)
        .then(response => response.json())
        .then(json => generateFormFields(formId, json, setCssClasses(), true));
}

formEl.onsubmit = e => {
    e.preventDefault();

    let formData = new FormData(formContainer);

    let fieldList = [];
    for (let [id, value] of formData) {
        fieldList.push({ id, value });
    }

    fetch(camundaUrl + `/submit-form?piId=${procInstanceId}&taskId=${taskId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(fieldList)
    }).then(() => {
        window.location.href = './homepage.html';
    });
}

let setCssClasses = () => {
    let css = new Map();

    css.set('lbl-firstName', 'form-label');
    css.set('firstName', 'form-control mb-2');
    css.set('lbl-lastName', 'form-label');
    css.set('lastName', 'form-control mb-2');
    css.set('lbl-phoneNum', 'form-label');
    css.set('phoneNum', 'form-control mb-2');
    css.set('lbl-vehicle', 'form-label');
    css.set('vehicle', 'form-control mb-2');
    css.set('lbl-dateTime', 'form-label');
    css.set('dateTime', 'form-select mb-2');

    return css;
}