$(function() {
	
	$("header").click(function() {
		document.location.href = rootPath + "/"
	})
	
	$("ul.tab li").click(function() {
		var activeTab = $(this).data("tab")
		
		$("ul.tab li").removeClass("tab_active")
		$(this).addClass("tab_active")
		
		if(activeTab == "tab_all") {
			$.ajax({
				url: rootPath + "/sc_all",
				type: "POST",
				success: function(result) {
					$(".bucket_table").html(result)
				},
				error: function(error) {
					alert("서버 통신 오류")
				}
			})
		} else if(activeTab == "tab_incomplete") {
			$.ajax({
				url: rootPath + "/sc_false",
				type: "POST",
				success: function(result) {
					$(".bucket_table").html(result)
				},
				error: function(error) {
					alert("서버 통신 오류")
				}
			})
		} else if(activeTab == "tab_complete") {
			$.ajax({
				url: rootPath + "/sc_true",
				type: "POST",
				success: function(result) {
					$(".bucket_table").html(result)
				},
				error: function(error) {
					alert("서버 통신 오류")
				}
			})
		}
	})
})