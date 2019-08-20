
// return false if endDate is before startDate
 // else true
function compareDate(startDate, endDate) {
    // part[0] : year
    // part[1] : month
    // part[2] : day
    var startParts = startDate.split("/");
    var endParts = endDate.split("/");
    Number
    console.log(startParts[0] + "-" + startParts[1] + "-" + startParts[2]);
    console.log(endParts[0] + "-" + endParts[1] + "-" + endParts[2]);

    if (!(Number(startParts[0]) === Number(endParts[0])))
        return Number(endParts[0]) > Number(startParts[0]);

    if (!(Number(startParts[1]) === Number(endParts[1])))
        return Number(endParts[1]) > Number(startParts[1]);

    if (!(Number(startParts[2]) === Number(endParts[2])))
        return Number(endParts[2]) > Number(startParts[2]);

    return true;

}