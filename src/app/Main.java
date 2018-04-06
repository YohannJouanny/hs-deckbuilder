package app;
import model.Model;
import vue.Vue;



public class Main {
	
	public static void main(String[] args) {
		Model model = new Model();
		new Vue(model);
	}
}
