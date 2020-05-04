<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ include file="../include/header.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
<script type="text/javascript">
	$(function () {
		$("#btnList").click(function () {
			location.href="${pageContext.request.contextPath}/sboard/listPage?&page=${page }&searchType=${searchType }&keyword=${keyword }";
		})
		
		$("#btnModify").click(function () {
			var bno = $("#bno").val();
			location.href="${pageContext.request.contextPath}/sboard/modifyPage?bno=${board.bno}&page=${page}&searchType=${searchType }&keyword=${keyword}";
		})
		
		$("#btnRemove").click(function () {
			var bno = $("#bno").val();
			var con = confirm("정말 삭제하시겠습니까?");
			console.log(con);
			if(con == true){
				location.href="${pageContext.request.contextPath}/sboard/removePage?bno=${board.bno}&page=${page}&searchType=${searchType }&keyword=${keyword}";	
			}else{
				return false;
			}
		})
	})
</script>
<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Read</h3>
				</div>
				<div class="box-body">
					<div class="form-group">
						<label>BNO</label>
						<input type="text" class="form-control" value="${board.bno }" readonly id="bno">
					</div>
					<div class="form-group">
						<label>Title</label>
						<input type="text" class="form-control" value="${board.title }" readonly>
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea rows="5" cols="30" class="form-control" readonly>${board.content }</textarea>
					</div>
					<div class="form-group">
						<label>Writer</label>
						<input type="text" class="form-control" value="${board.writer }" readonly>
					</div>
					<div class="form-group">
						<c:forEach var="file" items="${board.files }">
							<img src="displayFile?filename=${file }">
						</c:forEach>
					</div>
				</div>
				<div class="box-footer">
					<button class="btn btn-warning" id="btnModify">Modify</button>
					<button class="btn btn-danger" id="btnRemove">Remove</button>
					<button class="btn btn-primary" id="btnList">Go List</button>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				<div class="box-body">
					<label>Writer</label>
					<input type="text" class="form-control" placeholder="User Id" id="newReplyWriter">
					<label>Reply Text</label>
					<input type="text" class="form-control" placeholder="text" id="newReplyText">
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="btnReplyAdd">REPLY</button>
				</div>
			</div>
			<ul class="timeline">
				<li class="time-label" id="repliesDiv">
					<span class="bg-green">Replies List<span id="replycnt">[${board.replycnt }]</span></span>
				</li>
			</ul>
			<div class="text-center">
				<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
			</div>
		</div>
	</div>
</div>
	
	<div id="modifyModal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">22</h4>
				</div>
				<div class="modal-body">
					<p>
						<input type="text" id="replytext" class="form-control">
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" id="btnModSave">Modify</button>
				</div>
			</div>
		</div>
	</div>

<script id="template" type="text/x-handlebars-template">
	{{#each list}}
	<li class="replyLi" data-rno = "{{rno}}">
		<i class="fa fa-comments bg-blue"></i>
		<div class="timeline-item">
			<span class="time">
				<i class="fa fa-clock-o"></i>{{dateHelper regdate}}
			</span>
			<h3 class="timeline-header"><strong>{{rno}}</strong>-{{replyer}}</h3>
			<div class="timeline-body">{{replytext}}</div>
			<div class="timeline-footer">
				<a class="btn btn-primary btn-xs btnMod" data-rno = "{{rno}}" data-text = "{{replytext}}" data-toggle="modal" data-target="#modifyModal">Modify</a>
				<a class="btn btn-danger btn-xs btnDel" data-rno = "{{rno}}">Delete</a>
			</div>
		</div>
	</li>
	{{/each}}
</script>

<script>
	Handlebars.registerHelper("dateHelper", function (value) {
		var d = new Date(value);
		var year = d.getFullYear();
		var month = d.getMonth()+1;
		var date = d.getDate();
		
		return year+"/"+month +"/"+date;
	})
	
	var currentPage = 1;
	
	function getPageList( page ) {
		var bno = ${board.bno }
		$.ajax({
			url: "${pageContext.request.contextPath}/replies/"+bno+"/"+page,
			type: "get",
			data: "json",
			success:function(res){
				console.log(res);
				/* $("#list").empty(); */
				
				$(".replyLi").remove();
				var source = $("#template").html();
				var func = Handlebars.compile(source);
				$(".timeline").append(func(res));
				
				$("#replycnt").html("["+res.pageMaker.totalCount+"]");	
				
				$("#pagination").empty();
				for(var i = res.pageMaker.startPage; i<= res.pageMaker.endPage; i++){
					var $li = $("<li>");
					if(i==currentPage){
						$li.addClass("active");
					}
					var $a = $("<a>").html(i);
					$li.append($a);
					$("#pagination").append($li);
				}
			}
		})
	}
	$("#repliesDiv").click(function() {
		getPageList(1);
	})
	
	$(document).on("click", "#pagination li", function () {
		var no = $(this).text();
		currentPage = no;
		getPageList(no);
	})
	
	$(document).on("click", ".btnDel", function () {
		var rno = $(this).attr("data-rno");
		$.ajax({
			url:"${pageContext.request.contextPath}/replies/"+rno,
			type:"delete",
			dataType:"text",
			success:function(res){
				if(res == "SUCCESS"){
					alert("댓글이 삭제되었습니다.");
					getPageList(currentPage);
				}
			}
		})
	})
	
	$("#btnReplyAdd").click(function () {
		var bno = ${board.bno };
		var replyer = $("#newReplyWriter").val();
		var replytext = $("#newReplyText").val();
		
		var json = JSON.stringify({"bno":bno, "replyer":replyer, "replytext":replytext});
		
		$.ajax({
			url:"${pageContext.request.contextPath}/replies/",
			type:"post",
			headers:{"Content-Type":"application/json"},
			data:json,
			dataType:"text",
			success:function(res){
				if(res=="SUCCESS"){
					alert("댓글이 틍록되었습니다.");
					$("#newReplyWriter").val("");
					$("#newReplyText").val("");
					getPageList(1);
				}
			}
		})
	})
	
	$(document).on("click",".btnMod", function () {
			var rno = $(this).attr("data-rno");
			var replytext = $(this).attr("data-text");
			$(".modal-title").text(rno);
			$("#replytext").val(replytext);
	})
	
	$("#btnModSave").click(function() {
			var rno = $(".modal-title").text();
			var replytext = $("#replytext").val();
			var json = JSON.stringify({"replytext":replytext});
			
			$.ajax({
				url:"${pageContext.request.contextPath}/replies/"+rno,
				type:"put",
				headers:{"Content-Type":"application/json"},
				data:json,
				dataType:"text",
				success:function(res){
					console.log(res);
					if(res == "SUCCESS"){
						alert("댓글이 수정되었습니다.");
						getPageList(currentPage);
					}
					$("#modifyModal").modal("hide");
				}
			})
		})
	
</script>

<%@ include file="../include/footer.jsp" %>