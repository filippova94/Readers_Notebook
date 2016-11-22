
public abstract class PageController {

	abstract String getPage(String content, int current_page);
	abstract int getCountPage(String content, int count_symbol);
}
