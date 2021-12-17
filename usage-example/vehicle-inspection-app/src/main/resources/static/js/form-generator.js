// Generise HTML formu na osnovu vrednosti definisanih u Camunda Modeler-u
function generateFormFields(formId, jsonResponse, css = null, withLabels = false) {
    let fragment = new DocumentFragment();

    jsonResponse.formFields.forEach(field => {
        let div = createFormField(field, css, withLabels);
        fragment.append(div);
    });

    document.getElementById(formId).prepend(fragment);
}

let createFormField = (field, css, withLabels) => {
    let formField;
    let div = document.createElement('div');
    div.id = 'div-' + field.id;

    switch (field.type.name) {
        case 'string':
        case 'long':
        case 'integer':
        case 'double':
            formField = createInput(field, withLabels);
            div.append(formField);
            break;
        case 'file':
        case 'date':
        case 'time':
            formField = createInput(field, true);
            div.append(formField);
            break;
        case 'textarea':
            formField = createTextArea(field, withLabels);
            div.append(formField);
            break;
        case 'enum':
            formField = createSelect(field, withLabels);
            div.append(formField);
            break;
        case 'boolean':
            formField = createCheckbox(field);            
            div.append(formField);
            break;
        case 'radio':
            formField = createRadioGroup(field);
            div.append(formField);
            break;
        default:
            div = new DocumentFragment();
    }

    if (css) {
        setCss(div, css);
    }

    return div;
}

let createLabel = (id, label, ending = ': ') => {
    let formLabel = document.createElement('label');
    formLabel.id = 'lbl-' + id;
    formLabel.htmlFor = id;
    formLabel.innerText = label + ending;

    return formLabel;
}

let createInput = (field, withLabels) => {
    let inputEl = document.createElement('input');
    inputEl.type = getInputType(field.type.name, field.properties.subtype);
    inputEl.id = field.id;
    inputEl.name = field.id;
    setConstraints(inputEl, field.validationConstraints);

    let fragment = new DocumentFragment();
    
    if (withLabels) {
        let label = createLabel(field.id, field.label);
        let brEl = document.createElement('br');
        fragment.append(label, brEl, inputEl);
    } else {
        inputEl.placeholder = field.label;
        fragment.append(inputEl);
    }

    return fragment;
}

let createTextArea = (field, withLabels) => {
    let textAreaEl = document.createElement('textarea');
    textAreaEl.id = field.id;
    textAreaEl.name = field.id;
    if (field.properties.rows) {
        textAreaEl.rows = field.properties.rows;
    }
    if (field.properties.cols) {
        textAreaEl.cols = field.properties.cols;
    }
    setConstraints(textAreaEl, field.validationConstraints);

    let fragment = new DocumentFragment();
    
    if (withLabels) {
        let label = createLabel(field.id, field.label);
        let brEl = document.createElement('br');
        fragment.append(label, brEl, textAreaEl);
    } else {
        textAreaEl.placeholder = field.label;
        fragment.append(textAreaEl);
    }

    return fragment;
}

let createSelect = (field, withLabels) => {
    let selectEl = document.createElement('select');
    selectEl.id = field.id;
    selectEl.name = field.id;
    setConstraints(selectEl, field.validationConstraints);

    const options = field.type.values;
    for (let key in options) {
        let optionEl = document.createElement('option');
        optionEl.value = key;
        optionEl.innerText = options[key];
        selectEl.append(optionEl);
    }

    let fragment = new DocumentFragment();
    
    if (withLabels) {
        let label = createLabel(field.id, field.label);
        let brEl = document.createElement('br');
        fragment.append(label, brEl, selectEl);
    } else {
        let placeholderEl = document.createElement('option');
        placeholderEl.value = "";
        placeholderEl.innerText = field.label;
        placeholderEl.disabled = true;
        placeholderEl.selected = true;
        placeholderEl.hidden = true;
        selectEl.append(placeholderEl);
        fragment.append(selectEl);
    }

    return fragment;
}

let createCheckbox = (field) => {
    let checkboxEl = document.createElement('input');
    checkboxEl.type = 'checkbox';
    checkboxEl.id = field.id;
    checkboxEl.name = field.id;
    checkboxEl.value = 'true';
    setConstraints(checkboxEl, field.validationConstraints);

    if (field.properties.checked === 'true') {
        checkboxEl.checked = true;
    }

    let label = createLabel(field.id, field.label, '');

    let fragment = new DocumentFragment();
    fragment.append(checkboxEl, label);

    return fragment;
}

let createRadioGroup = (field) => {
    let radioGroup = new DocumentFragment();
    let chosen = field.properties.chosen;

    for (let id in field.properties) {
        if (id !== 'chosen') {
            let radioBtn = createRadioBtn(field, id, chosen);
            let label = createLabel(field.id + '-' + id, field.properties[id], '');
            let div = document.createElement('div');
            div.id = 'div-' + field.id + '-' + id;
            div.append(radioBtn, label);
            radioGroup.append(div);
        }
    }

    return radioGroup;
}

let createRadioBtn = (field, btnId, chosen) => {
    let radioEl = document.createElement('input');
    radioEl.type = 'radio';
    radioEl.id = field.id + '-' + btnId;
    radioEl.name = field.id;
    radioEl.value = btnId;
    setConstraints(radioEl, field.validationConstraints);

    if (btnId === chosen) {
        radioEl.checked = true;
    }

    return radioEl;
}

// Za dati element postavlja Validation Constraints definisane u Camunda Modeler-u
function setConstraints(element, constraints) {
    constraints.forEach(constraint => {
        element.setAttribute(constraint.name, constraint.configuration);
    });
}

// Postavlja CSS klase prosledjenom elementu i svim ugnjezdenim elementima
function setCss(parentEl, css) {
    let classes = css.get(parentEl.id);
    if (classes) {
        let classList = classes.split(' ');
        parentEl.classList.add(...classList);
    }

    let children = parentEl.children;
    for (let i = 0; i < children.length; i++) {
        setCss(children[i], css);
    }
}

// Spaja selektovane opcije u jedan string
// da bi Camunda-in submit forme na backend-u bio uspesan
function mergeSelectedOptions(formData, selectName) {
    let selectedOptions = formData.getAll(selectName);
    formData.delete(selectName);
    formData.set(selectName, selectedOptions.join('|&|') + '|&|');
}

// Dodaje neselektovane checkbox dugmice kao prazne vrednosti
// da bi Camunda-in submit forme na backend-u bio uspesan
function appendUncheckedCheckboxes(formData, formEl) {
    let unchecked = formEl.querySelectorAll('input[type="checkbox"]:not(:checked)');
    unchecked.forEach(checkbox => {
        formData.set(checkbox.id, 'false');
    });
}

// Menja podrazumevani format svih datuma u format 'dd/MM/yyyy'
// da bi Camunda-in submit forme na backend-u bio uspesan
function convertDates(formData, formEl) {
    let dates = formEl.querySelectorAll('input[type="date"]');
    dates.forEach(dateEl => {
        let parts = formData.get(dateEl.name).split('-');
        let newValue = `${parts[2]}/${parts[1]}/${parts[0]}`;
        formData.set(dateEl.name, newValue);
    })
}

// Vraca odgovarajuci tip za input polje
let getInputType = (fieldType, fieldSubType) => {
    switch (fieldType) {
        case 'string':
            return fieldSubType ? fieldSubType : 'text';
        case 'long':
        case 'integer':
        case 'double':
            return 'number';
        default:
            return fieldType;
    }
}