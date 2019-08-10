function teacherNmeFormatter(value, row, index) {
    if (jQuery.isEmptyObject(row.teacher))
        return "-";
    else
        return row.teacher.firstName + " " + row.teacher.lastName;
}

function roleFormatter(value, row, index) {
    var text = "";

        if (row.account.role.name == "STUDENT")
            text = "دانشجو";
        else if (row.account.role.name == "TEACHER")
            text = "استاد";
    return text;
}

function enabledFormatter(value, row, index) {
    var text = "";

    if (row.account.enabled)
        return "فعال";
    else
        return "غیرفعال";
}