var dataTables = [];
var dataTableParams = [];
$(document).ready(function () {
    $('.dataTables').each(function (i, dt) {
        var tableId = $(this).attr('id');
        dataTableParams[tableId + ''] = {};
        var paramStr = $(this).attr("data-params");
        if (paramStr) {
            paramStr = "{" + jewelry.replaceAll(paramStr, "'", "\"") + "}";
            data = $.parseJSON(paramStr);
            for (var p in data) {
                dataTableParams[tableId + ''][p] = data[p];
            }
        }

        var optStr = "{" + $(this).attr("data-options") + "}";
        optStr = jewelry.replaceAll(optStr, "'", "\"");
        var opt = $.parseJSON(optStr);

        var searching = false;
        if (opt.searching) {
            searching = true;
        }

        var pageLength = 10;
        var serverSide = false;
        if (opt.serverSide) {
            serverSide = true;
        } else {
            if (!searching) {
                searching = true;
            }
            if (opt.pageSize) {
                pageLength = opt.pageSize;
            } else {
                pageLength = 100;
            }
        }

        var ordering = false;
        var order = [[0, "asc"]];
        if (opt.order) {
            var str = opt.order;
            var args = str.split("-");
            order = [[args[0], args[1]]];
            ordering = true;
        }

        var columnDefs = [];
        var columns = [];
        $(this).find('thead>tr>th').each(function (i, col) {
            var colOptStr = "{" + $(this).attr("data-options") + "}";
            colOptStr = jewelry.replaceAll(colOptStr, "'", "\"");
            var colOpt = $.parseJSON(colOptStr);
            columns[i] = {"data": colOpt.data};
            var renderFunc = jewelry.strToFunc('' + colOpt.render);
            columnDefs[i] = {"targets": i, "render": renderFunc, "visible": colOpt.visible};
        });

        dataTables[tableId + ''] = $('#' + tableId).DataTable({
            "serverSide": serverSide,
            "searching": searching,
            "ordering": ordering,
            "order": order,
            "pageLength": pageLength,
            "language": {
                "url": siteConf.resHost + "/js/datatables/js/jquery.dataTables-cn.json"
            },
            "ajax": {
                "url": opt.url, "type": "post", "data": function (d) {
                    for (var p in dataTableParams[tableId + '']) {
                        d[p] = dataTableParams[tableId + ''][p];
                    }
                    return d;
                }
            },
            "columnDefs": columnDefs,
            "columns": columns
        });
        dataTables[tableId + '']['selected'] = [];
        $('#' + tableId + ' tbody').on('click', 'tr', function () {
            var data = dataTables[tableId + ''].row(this).data();
            var index = $.inArray(data, dataTables[tableId + '']['selected']);
            if (index === -1) {
                dataTables[tableId + '']['selected'].push(data);
            } else {
                dataTables[tableId + '']['selected'].splice(index, 1);
            }
            $(this).toggleClass('selected');
        });
    });

    $('.dtSelectedOps').each(function (i, dt) {
        var optStr = "{" + $(this).attr("data-options") + "}";
        optStr = jewelry.replaceAll(optStr, "'", "\"");
        var opt = $.parseJSON(optStr);
        var url = opt.url;
        var table = 'dt';
        var col = 'id';
        var tip = $(this).text();
        if (opt.table) {
            table = opt.table;
        }
        if (opt.col) {
            col = opt.col;
        }
        if (opt.tip) {
            tip = opt.tip;
        }
        var all = false;
        if (opt.all) {
            all = true;
        }
        $(this).click(function () {
            if (!all && dataTables[table]['selected'].length === 0) {
                jewelry.notify("请选择要" + tip + "的记录", "warning");
                return;
            }
            jewelry.confirm("确认" + tip, function (ok) {
                if (ok) {
                    var ids = [];
                    if (!all) {
                        for (var i = 0; i < dataTables[table]['selected'].length; i++) {
                            ids.push(dataTables[table]['selected'][i][col]);
                        }
                    }
                    $.post(url, {ids: ids}, function (result) {
                        if (result.code == 0) {
                            jewelry.notify(tip + "成功");
                            jewelry.reloadDatatable(table);
                        }
                    });
                }
            });
        });
    });

    $('.dtCreateOps').each(function (i, dt) {
        var tip = $(this).text();
        var modal = "#" + $(this).attr("data-modal");
        var table = $(modal).attr("data-table");
        $(this).click(function () {
            $(modal).find("modal-title").each(function () {
                $(this).innerText = tip;
            });
            $(modal).find("form")[0].reset();
            $(modal).find("input").each(function () {
                if ($(this).attr('data-def')) {
                    $(this).val($(this).attr('data-def'));
                } else {
                    $(this).val('');
                }
            });
            $(modal).modal('show');
        });
    });

    $('.dtEditOps').each(function (i, dt) {
        var tip = $(this).text();
        var modal = "#" + $(this).attr("data-modal");
        var table = $(modal).attr("data-table");
        $(this).click(function () {
            var data = jewelry.dtSingleSelected(table);
            if (!data) {
                return;
            }
            for (d in data) {
                $(modal).find("*[name='" + d + "']").each(function () {
                    $(this).val(data[d]);
                });
            }

            $(modal).find("modal-title").each(function () {
                $(this).innerText = tip;
            });
            $(modal).modal('show');
        });
    });

    $('.editModal').each(function (i, dt) {
        var table = $(this).attr('data-table');
        var forms = $(this).find("form");
        var modal = $(this);
        forms.each(function () {
            var enctype = $(this).attr("enctype");
            var url = $(this).attr("action");
            if (enctype == "multipart/form-data") {
                $(this).ajaxform({
                    "complete": function (result) {
                        result = JSON.parse(result);
                        if (result.code !== 0) {
                            jewelry.notify(result.msg, "error");
                        } else {
                            modal.modal("hide");
                            jewelry.notify("操作成功");
                            jewelry.reloadDatatable(table);
                        }
                    }
                });
            } else {
                var form = $(this);
                $(this).find("button[data-target='submit']").click(function (e) {
                    e.preventDefault();
                    $.post(url, form.serialize(), function (result) {
                        if (result.code == 0) {
                            modal.modal("hide");
                            jewelry.notify("操作成功");
                            jewelry.reloadDatatable(table);
                        }
                    });
                });
            }
        });
    });
});

function cidRender(data, type, row) {
    if (data === 0) {
        return "";
    }
    if (typeof categoryMap[data] !== 'undefined') {
        return categoryMap[data].name;
    } else {
        return "";
    }
}

function pidRender(data, type, row) {
    if (data === 0) {
        return "";
    }
    if (typeof categoryMap[data] !== 'undefined' && typeof categoryMap[categoryMap[data].pid] !== 'undefined') {
        return categoryMap[categoryMap[data].pid].name;
    } else {
        return "";
    }
}