<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ include file="../include/header.jsp" %>

<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">List</h3>
				</div>
				<div class="box-body text-right">
					<a href="${pageContext.request.contextPath }/board/register">register</a>
				</div>
				<div class="box-body">
					<table class="table table-bordered">
						<tr>
							<th style="width: 10px;">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width: 40px;">VIEWCNT</th>
						</tr>
						<c:forEach var="board" items="${list }">
							<tr>
								<td>${board.bno }</td>
								<td><a href="${pageContext.request.contextPath }/board/readPage?bno=${board.bno }&page=${pageMaker.cri.page}">${board.title }</a></td>
								<td>${board.writer }</td>
								<td><fmt:formatDate value="${board.regdate }" pattern="yyyy-MM-dd, HH:mm"/></td>
								<td><span class="badge bg-red">${board.viewcnt }</span></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev == true }">
								<li><a href="listPage?page=${pageMaker.startPage-1 }">&laquo;</a></li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li class="${pageMaker.cri.page == idx?'active':'' }"><a href="listPage?page=${idx }">${idx }</a></li>
							</c:forEach>
							<c:if test="${pageMaker.next == true }">
								<li><a href="listPage?page=${pageMaker.endPage+1 }">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp" %>