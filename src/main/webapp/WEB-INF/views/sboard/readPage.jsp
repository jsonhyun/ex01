<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ include file="../include/header.jsp" %>
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
				</div>
				<div class="box-footer">
					<button class="btn btn-warning" id="btnModify">Modify</button>
					<button class="btn btn-danger" id="btnRemove">Remove</button>
					<button class="btn btn-primary" id="btnList">Go List</button>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp" %>