function teacherNmeFormatter(value, row, index) {
    if (jQuery.isEmptyObject(row.teacher))
        return "-";
    else
        return row.teacher.firstName + " " + row.teacher.lastName;
}
