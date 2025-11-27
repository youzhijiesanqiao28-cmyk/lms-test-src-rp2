package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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

		String actualCourseName = webDriver.findElement(
				By.cssSelector("h2.di")).getText();

		assertTrue(actualCourseName.startsWith("DEMOコース"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 機能メニューの『ヘルプ』からヘルプ画面に遷移し表示を確認")
	void test03() {

		// 機能メニューのドロップダウンを開くまで待つ
		visibilityTimeout(By.linkText("機能"), 10);

		// 「機能」をクリックしてドロップダウン表示
		webDriver.findElement(By.linkText("機能")).click();

		// 「ヘルプ」が表示されるまで待機
		visibilityTimeout(By.linkText("ヘルプ"), 10);

		// 「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// ページロード待ち
		pageLoadTimeout(5);

		// 現在のURL取得
		String currentUrl = webDriver.getCurrentUrl();

		// URL が /lms/help であることを確認
		//assertEquals("http://localhost:8080/lms/help", currentUrl);

		// コンソールへ出力
		//System.out.println("現在のURL: " + currentUrl);

		// h2 タグの文言が「ヘルプ」であることを確認
		String title = webDriver.findElement(By.tagName("h2")).getText();
		assertTrue(title.startsWith("ヘルプ"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		//リンクが表示されるまで待機
		visibilityTimeout(By.linkText("よくある質問"), 10);

		//現在のタブを保存
		//String originalTab = webDriver.getWindowHandle();

		//新規タブで開く
		webDriver.findElement(By.linkText("よくある質問")).click();

		//新しいタブ一覧を取得
		java.util.List<String> tabs = new java.util.ArrayList<>(webDriver.getWindowHandles());

		// 新しいタブへ切り替え
		webDriver.switchTo().window(tabs.get(1));

		// 6. URL確認
		String currentUrl = webDriver.getCurrentUrl();
		System.out.println("【現在のURL】" + currentUrl);
		assertEquals("http://localhost:8080/lms/faq", currentUrl);

		// 7. h2の確認（ここでページ読み込みが完了しSelenium実行コンテキストも安定する）
		visibilityTimeout(By.tagName("h2"), 10);
		String h2Text = webDriver.findElement(By.tagName("h2")).getText();
		assertEquals("よくある質問", h2Text);

		//System.out.println("取得したh2: " + h2Text);

		getEvidence(new Object() {
		});
	}

}
