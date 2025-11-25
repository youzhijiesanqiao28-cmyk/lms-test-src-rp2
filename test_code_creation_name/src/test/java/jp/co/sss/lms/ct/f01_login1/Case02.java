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
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		//ログインIDの表示を待機
		visibilityTimeout(By.name("loginId"), 10);

		// 存在しないログインID・パスワードを入力
		webDriver.findElement(By.name("loginId")).sendKeys("test");
		webDriver.findElement(By.name("password")).sendKeys("test");

		// ログインボタン押下
		webDriver.findElement(By.cssSelector("input[value='ログイン']")).click();

		//エラーメッセージが表示されるまで待機
		visibilityTimeout(By.cssSelector("span.help-inline.error"), 10);

		//エラーメッセージの内容を確認
		String errorText = webDriver.findElement(By.cssSelector("span.help-inline.error")).getText();
		assertEquals("* ログインに失敗しました。", errorText);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
