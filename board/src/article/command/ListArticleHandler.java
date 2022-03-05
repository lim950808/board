package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler {

	private ListArticleService listService = new ListArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		String pageNoVal = req.getParameter("pageNo"); //읽어올 페이지번호를 구한다.
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = listService.getArticlePage(pageNo); //지정한 페이지번호에 해당하는 게시글 데이터를 구한다.
		req.setAttribute("articlePage", articlePage); //articlePage의 객체를 jsp에서 사용할 수 있도록 request의 articlePage 속성에 저장함.
		return "/WEB-INF/view/listArticle.jsp";
	}

}
