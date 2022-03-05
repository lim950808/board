package article.service;

import java.util.List;

import article.model.Article;

public class ArticlePage {

	private int total; //전체 게시글의 갯수
	private int currentPage; //사용자가 요청한 페이지 번호
	private List<Article> content; //화면에 출력할 게시글 목록
	private int totalPages; //전체 페이지의 갯수
	private int startPage; //화면 하단에 보여줄 페이지 이동링크의 시작번호
	private int endPage; //화면 하단에 보여줄 페이지 이동링크의 끝번호
//게시글 데이터와 페이징 관련 정보를 담을 ArticlePage 클래스를 구현.
	public ArticlePage(int total, int currentPage, int size, List<Article> content) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		if (total == 0) { //만약 전체 게시글 갯수가 0이면 totalPages, startPage, endPage를 모두 0으로 할당함.
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else {
			totalPages = total / size; //total은 전체 게시글 수. size는 한 페이지에 보여줄 게시글 갯수. 나누기(/)를 통해 얻어진 정수값이 페이지 번호.
			if (total % size > 0) { // %는 나머지 값을 표현하는 기호로 만약 그 값이 0 보다 크면 페이지 갯수가 하나 추가됨.
				totalPages++;
			}
			int modVal = currentPage % 5; //화면 하단의 페이지 이동링크의 페이지 번호를 구함. 현재페이지에서 앞뒤로 5개씩 표현함.
			startPage = currentPage / 5 * 5 + 1; //ex) 3(currentPage)/5*5+1=1
			if (modVal == 0) startPage -= 5;
			
			endPage = startPage + 4; //시작페이지에서 4를 추가한 값이 끝 페이지 번호
			if (endPage > totalPages) endPage = totalPages; //만약 끝번호가 총 페이지 수 보다 많다면 그 끝번호가 총 페이지 갯수가 된다. 
		}
	}

	public int getTotal() {
		return total;
	}

	public boolean hasNoArticles() {
		return total == 0;
	}

	public boolean hasArticles() {
		return total > 0;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<Article> getContent() {
		return content;
	}

	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
}
