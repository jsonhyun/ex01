<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ include file="../include/header.jsp" %>
<script type="text/javascript">
	$(function () {
		$("#btnCancel").click(function () {
			var bno = $("input[name='bno']").val();
			console.log(bno);
			location.href="${pageContext.request.contextPath}/board/readPage?bno=${board.bno}&page=${cri.page}";
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
				<form role="form" action="modifyPage" method="post">
					<div class="box-body">
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="Enter Title" value="${board.title }">
							<input type="hidden" name="bno" value="${board.bno }">
							<input type="hidden" name="page" value="${cri.page }">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="30" placeholder="Enter Content" name="content" class="form-control">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="Enter Writer"  value="${board.writer }" readonly>
						</div>
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

<%@ include file="../include/footer.jsp" %>