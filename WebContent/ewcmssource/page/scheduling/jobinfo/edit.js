$(function() {
	var monthsAllChecked = true;
	var weekDaysAllChecked = true;
	$("input[name='pageDisplayVo.months']").each(function() {
		if (!$(this).attr('checked')) {
			monthsAllChecked = false;
		}
	});
	if (monthsAllChecked) {
		$('#monthsAll').attr('checked', true);
	} else {
		$('#monthsAll').attr('checked', false);
	}
	$("input[name='pageDisplayVo.weekDays']").each(function() {
		if (!$(this).attr('checked')) {
			weekDaysAllChecked = false;
		}
	});
	if (weekDaysAllChecked) {
		$('#weekDaysAll').attr('checked', true);
	} else {
		$('#weekDaysAll').attr('checked', false);
	}
	$('#monthsAll').click(function() {
		if ($('#monthsAll').attr('checked') == 'checked') {
			$("input[name='pageDisplayVo.months']").attr('checked', true);
		} else {
			$("input[name='pageDisplayVo.months']").attr('checked', false);
		}
	});
	$('#weekDaysAll').click(function() {
		$("input[name='pageDisplayVo.days']").get(1).checked = true;
		if ($('#weekDaysAll').attr('checked') == 'checked') {
			$("input[name='pageDisplayVo.weekDays']").attr('checked', true);
		} else {
			$("input[name='pageDisplayVo.weekDays']").attr('checked', false);
		}
	});
	$("input[name='pageDisplayVo.weekDays']").click(function() {
		$("input[name='pageDisplayVo.days']").get(1).checked = true;
		var weekChecked = true;
		$("input[name='pageDisplayVo.weekDays']").each(function() {
			if (!$(this).attr('checked')) {
				weekChecked = false;
			}
		});
		if (weekChecked) {
			$('#weekDaysAll').attr('checked', true);
		} else {
			$('#weekDaysAll').attr('checked', false);
		}
	});
	$("input[name='pageDisplayVo.months']").click(function() {
		var monthsChecked = true;
		$("input[name='pageDisplayVo.months']").each(function() {
			if (!$(this).attr('checked')) {
				monthsChecked = false;
			}
		});
		if (monthsChecked) {
			$('#monthsAll').attr('checked', true);
		} else {
			$('#monthsAll').attr('checked', false);
		}
	});
	$('#monthDays').click(function() {
		$("input[name='pageDisplayVo.days']").get(2).checked = true;
	});
	$('#occurrenceCount').click(function() {
		$("input[name='pageDisplayVo.occur']").get(2).checked = true;
	});
	$("input[name='pageDisplayVo.days']").click(function() {
		if ($("input[name='pageDisplayVo.days']:checked").val() == 3) {
			$('#monthDays').focus();
		}
	});
	$("input[name='pageDisplayVo.occur']").click(function() {
		var occurId = $("input[name='pageDisplayVo.occur']:checked").val();
		if (occurId == 3) {
			$('#occurrenceCount').focus();
		} else if (occurId == 2) {
			$("input[name='pageDisplayVo.endDateSimple']").focus();
		}
	});
	$("input[name='pageDisplayVo.mode']").click(function() {
		var modeId = $("input[name='pageDisplayVo.mode']:checked").val();
		if (typeof modeId == 'undefined') {
			modeId = 1;
			$("input[name='pageDisplayVo.mode']").get(modeId).checked = true;
			$("input[name='pageDisplayVo.occur']").get(0).checked = true;
		}
		if (modeId == 0) {
			$('#trSimplicity').hide();
			$('#trComplexity').hide();
		} else if (modeId == 1) {
			$('#trSimplicity').show();
			$('#trComplexity').hide();
		} else {
			$('#trSimplicity').hide();
			$('#trComplexity').show();
		}
	});
	var modeId = $("input[name='pageDisplayVo.mode']:checked").val();
	if (typeof modeId == 'undefined' || $('#jobId').val() == "") {
		modeId = 1;
		$("input[name='pageDisplayVo.mode']").get(modeId).checked = true;
		$("input[name='pageDisplayVo.occur']").get(0).checked = true;
	}
	if (modeId == 0) {
		$('#trSimplicity').hide();
		$('#trComplexity').hide();
	} else if (modeId == 1) {
		$('#trSimplicity').show();
		$('#trComplexity').hide();
	} else {
		$('#trSimplicity').hide();
		$('#trComplexity').show();
	}
	var occurId = $("input[name='pageDisplayVo.occur']:checked").val();
	if (typeof occurId == 'undefined' || $('#jobId').val() == "") {
		$("input[name='pageDisplayVo.occur']").get(0).checked = true;
	}
	var daysId = $("input[name='pageDisplayVo.days']:checked").val();
	if (typeof daysId == 'undefined' || $('#jobId').val() == "") {
		$("input[name='pageDisplayVo.days']").get(0).checked = true;
	}
});
