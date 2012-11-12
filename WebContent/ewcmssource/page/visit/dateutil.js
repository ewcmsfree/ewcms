function dateTimeToString(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var arr = [];
	arr.push(y);
	arr.push(m > 9 ? m : "0" + m);
	arr.push(d > 9 ? d : "0" + d);
	return arr.join("-");
}