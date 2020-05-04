<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ include file="../include/header.jsp" %>
<style>
	.img{
		position: relative;
		float: left;
		margin-right: 10px;
		margin-bottom: 20px;
	}
	.chkbox{
		position: absolute;
		right: 0;
		top: 0;
	}
	#clear{
		clear: both;
	}
</style>
<script type="text/javascript">
	$(function () {
		$("#btnCancel").click(function () {
			var bno = $("input[name='bno']").val();
			console.log(bno);
			location.href="${pageContext.request.contextPath}/sboard/readPage?bno=${board.bno}&page=${page}&searchType=${searchType }&keyword=${keyword}";
		})
		
		$(".chkbox").click(function() {
			var flag = $(this).prop("checked");
			if(flag == true){
				$(this).parent().css("opacity", 0.5);
			}else{
				$(this).parent().css("opacity", 1);
			}
		})
	})
</script>
<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Modify</h3>
				</div>
				<form role="form" action="modifyPage" method="post" enctype="multipart/form-data">
					<div class="box-body">
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="Enter Title" value="${board.title }">
							<input type="hidden" name="bno" value="${board.bno }">
							<input type="hidden" name="page" value="${page }">
							<input type="hidden" name="searchType" value="${searchType }">
							<input type="hidden" name="keyword" value="${keyword }">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="30" placeholder="Enter Content" name="content" class="form-control">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="Enter Writer"  value="${board.writer }" readonly>
						</div>
						<div class="form-group">
						<c:forEach var="file" items="${board.files }">
							<div class="img">
								<img src="displayFile?filename=${file }">
								<input type="checkbox" class="chkbox" name="imgFile" value="${file }">
							</div>
						</c:forEach>
						</div>
						<div class="form-group" id="clear">
							<label>Image Files</label>
							<input type="file" name="imageFiles" id="files" class="form-control" multiple="multiple">
						</div>
						<div id="previewBox"></div>
					</div>
					<div class="box-footer">
						<button type="submit" class="btn btn-primary">Modify</button>
						<button type="button" class="btn btn-danger" id="btnCancel">Cancel</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	$("#files").change(function(){
		$("#previewBox").empty();
		var file = $(this)[0].files; //$(this)[0] :javascript객체
		$(file).each(function(i, obj) {
			var reader = new FileReader();//javascript 객체
			reader.readAsDataURL(file[i]);
			reader.onload = function(e){
				var $img = $("<img>").attr("src", e.target.result);
				$("#previewBox").append($img);
			}
		})
		console.log(file);
	})
</script>

<%@ include file="../include/footer.jsp" %>