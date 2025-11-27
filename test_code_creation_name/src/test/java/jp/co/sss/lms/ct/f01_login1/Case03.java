package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		//画面遷移
		goTo("http://localhost:8080/lms");

		// ページタイトルからログイン画面であることを検証
		String checkTitle = webDriver.getTitle();
		assertEquals("ログイン | LMS", checkTitle);

		// エビデンスの保存
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		//ログインIDの表示を待機
		visibilityTimeout(By.name("loginId"), 10);

		// 存在しないログインID・パスワードを入力
		webDriver.findElement(By.name("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.name("password")).sendKeys("uP61MpS4");

		// ログインボタン押下
		webDriver.findElement(By.cssSelector("input[value='ログイン']")).click();

		// ログイン成功画面の見出しが「DEMOコース」であるか判定
		String actualCourseName = webDriver.findElement(
				By.cssSelector("h2.di")).getText();

		assertTrue(actualCourseName.startsWith("DEMOコース"));

		// エビデンス取得
		getEvidence(new Object() {
		});

	}

}
