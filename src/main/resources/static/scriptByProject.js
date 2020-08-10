function getRecord() {
    var field = document.getElementById("get-record");
    if (field.value != "") {
        var table = document.getElementById("table");
        if (table != null) {
            table.remove();
        }
        var url = "./todo_list/" + field.value;
        field.value = "";
        fetch(url)
            .then(function (response) {
                if (response.ok) {
                    response.json().then(function (json) {
                        let table = '<table id="table" class="table">\n' +
                            '  <thead>\n' +
                            '    <tr>\n' +
                            '      <th scope="col">Id</th>\n' +
                            '      <th scope="col">Note</th>\n' +
                            '    </tr>\n' +
                            '  </thead>\n' +
                            '  <tbody>\n';

                        $.each(json.data, function (i, records) {
                            table +=
                                '<tr>\n' +
                                '    <th scope="row">' + records.id + '</th>\n' +
                                '    <td>' + records.note + '</td>\n' +
                                '</tr>';
                        });

                        table += '  </tbody>\n' +
                            '</table>';
                        var list = document.createRange()
                            .createContextualFragment(table);
                        document.getElementById('main-block').after(list);
                    })
                } else {
                    response.json().then(function (json) {
                        alert(json.message + "\n");
                    })

                }
            })
    } else {
        alert("Please enter id");
    }
}

function getRecords() {
    var field = document.getElementById("get-records");
    var url = "./todo_list/";
    var table = document.getElementById("table");
    if (table != null) {
        table.remove();
    }
    fetch(url)
        .then(function (response) {
            if (response.ok) {
                response.json().then(function (json) {
                    let table = '<table id="table" class="table">\n' +
                        '  <thead>\n' +
                        '    <tr>\n' +
                        '      <th scope="col">Id</th>\n' +
                        '      <th scope="col">Note</th>\n' +
                        '    </tr>\n' +
                        '  </thead>\n' +
                        '  <tbody>\n';

                    $.each(json.data, function (i, records) {
                        table +=
                            '<tr>\n' +
                            '    <th scope="row">' + records.id + '</th>\n' +
                            '    <td>' + records.note + '</td>\n' +
                            '</tr>';
                    });

                    table += '  </tbody>\n' +
                        '</table>';
                    var list = document.createRange()
                        .createContextualFragment(table);
                    document.getElementById('main-block').after(list);
                })
            } else {
                response.json().then(function (json) {
                    alert(json.message + "\n");
                })
            }
        })
}

function deleteRecordById() {
    var field = document.getElementById("delete-record");
    if (field.value != "") {
        var url = "./todo_list/" + field.value;
        field.value = "";
        fetch(url, {method: 'DELETE'})
            .then(function (response) {
                if (response.ok) {
                    response.json().then(function (json) {
                        alert(json.message);
                    })
                } else {
                    response.json().then(function (json) {
                        alert(json.message);
                    })
                }
            })
    } else {
        alert("Please enter id");
    }
}

function deleteAll() {
    var url = "./todo_list";
    fetch(url, {method: 'DELETE'})
        .then(function (response) {
            if (response.ok) {
                response.json().then(function (json) {
                    alert(json.message);
                })
            } else {
                response.json().then(function (json) {
                    alert(json.message);
                })
            }
        })
}

function editRecordById() {
    var idRecord = document.getElementById("id-record");
    var valueRecord = document.getElementById("value-record");
    if (idRecord.value != "" && valueRecord != "") {
        var url = "./todo_list/" + idRecord.value + "?" + $.param({note: valueRecord.value});
        fetch(url, {method: 'PUT'})
            .then(function (response) {
                if (response.ok) {
                    response.json().then(function (json) {
                        alert(json.message);
                    })
                } else {
                    response.json().then(function (json) {
                        alert(json.message);
                    })
                }
            })
        idRecord.value = "";
        valueRecord.value = "";
    } else {
        alert("Please enter data");
    }
}

var i = 1;

function createEl(e) {
    let sourceId = e.parentNode.parentNode.id;

    if (i != sourceId) {
        i = sourceId;
    }
    let parentDiv = document.getElementById(i);
    parentDiv.querySelector("#insert").setAttribute("disabled", "disabled");
    i++;
    var divNext = parentDiv.cloneNode(true);
    divNext.setAttribute("id", i);
    divNext.querySelector("#insert").removeAttribute("disabled");
    divNext.querySelector("#remove").removeAttribute("disabled");
    divNext.querySelector("#id-record").value = "";
    divNext.querySelector("#value-record").value = "";

    parentDiv.after(divNext);
}

function addNewRecord() {
    var elements = document.getElementsByName("params");
    var params = [];
    for (var x = 0; x < elements.length; x++) {
        var idRecord = elements[x].querySelector("#id-record");
        var valueRecord = elements[x].querySelector("#value-record");
        let json = {
            "id": idRecord.value,
            "note": valueRecord.value
        }
        params.push(json);
    }
    if (idRecord.value != "" && valueRecord != "") {
        var url = "./todo_list"
        fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params)
        })
            .then(function (response) {
                if (response.ok) {
                    response.json().then(function (json) {
                        alert(json.message);
                    })
                } else {
                    response.json().then(function (json) {
                        alert(json.message);
                    })
                }
            })

    } else {
        alert("Please enter data");
    }
    for (var x = elements.length - 1; x >= 0; x--) {
        if (x == 0) {
            elements[x].querySelector("#id-record").value = "";
            elements[x].querySelector("#value-record").value = "";
            elements[x].querySelector("#insert").removeAttribute("disabled");
            elements[x].querySelector("#remove").setAttribute("disabled", "true");
        } else {
            elements[x].remove();
        }
    }

}

function deleteEl(e) {
    let sourceId = e.parentNode.parentNode.id;
    let parentDiv = document.getElementById(sourceId);
    i--;
    if (i == 1){
        document.getElementById("insert").removeAttribute("disabled")
    }
    parentDiv.remove();

}
