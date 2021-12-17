let createTableRow = (innerTh, innerTd) => {
    let trEl = document.createElement('tr');

    if (innerTh) {
        let thEl = document.createElement('th');
        thEl.append(innerTh);
        trEl.append(thEl);
    }

    let tdEl = document.createElement('td');
    tdEl.append(innerTd);
    trEl.append(tdEl);

    return trEl;
}

