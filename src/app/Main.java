package app;
import model.Model;
import vue.Vue;



public class Main {
	
	public static final String VERSION = "2.2.0";
	
	public static void main(String[] args) {
		Model model = new Model();
		new Vue(model);
	}
}
