package testcases;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCase3 {

	@Test(dataProvider="getData")
	public void doLogin(String un,String pwd) {

		System.out.println("Executing Login Test with " + un + " and " + pwd);
	}

	
	/*
	 * @Test public void isSkip() {
	 * 
	 * throw new SkipException("Skipping the test case"); }
	 */
	 
	@DataProvider
	public Object[] getData()
	{ 
		Object data[][] = new Object[6][2];
		data[0][0]="admin";
		data[0][1]="pwd-admin";
		
		data[1][0]="padmin";
		data[1][1]="pwd-padmin";
		
		data[2][0]="gadmin";
		data[2][1]="pwd-gadmin";
		
		
		data[3][0]="admin";
		data[3][1]="pwd-admin";
		
		data[4][0]="padmin";
		data[4][1]="pwd-padmin";
		
		data[5][0]="gadmin";
		data[5][1]="pwd-gadmin";
		return data;
	}
}
