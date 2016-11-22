
public class TxtPageController extends PageController{
	
	String getPage(String content, int current_page) {
		String text;
		int count_page = getCountPage(content,2400);
		if (current_page <= count_page) {
			text = content.substring(current_page * 2400,
					(current_page + 1) * 2400);
		} else {
			text = "КОНЕЦ";
		}
		return text;
	}
	int getCountPage(String content, int count_symbol)
	{
		return content.length()/count_symbol;
	}
	
}
