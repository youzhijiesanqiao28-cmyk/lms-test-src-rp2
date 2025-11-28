package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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

		// 存在するログインID・パスワードを入力
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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 機能メニューのドロップダウンを開くまで待つ
		visibilityTimeout(By.linkText("機能"), 10);

		// 「機能」をクリックしてドロップダウン表示
		webDriver.findElement(By.linkText("機能")).click();

		// 「ヘルプ」が表示されるまで待機
		visibilityTimeout(By.linkText("ヘルプ"), 10);

		// 「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		pageLoadTimeout(5);

		// 現在のURL取得
		String currentUrl = webDriver.getCurrentUrl();

		// h2 タグの文字が「ヘルプ」であることを確認
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

		//新規タブで開く
		webDriver.findElement(By.linkText("よくある質問")).click();

		//新しいタブ一覧を取得
		java.util.List<String> tabs = new java.util.ArrayList<>(webDriver.getWindowHandles());

		// 新しいタブへ切り替え
		webDriver.switchTo().window(tabs.get(1));

		//URL確認
		String currentUrl = webDriver.getCurrentUrl();
		//System.out.println("【現在のURL】" + currentUrl);
		assertEquals("http://localhost:8080/lms/faq", currentUrl);

		visibilityTimeout(By.tagName("h2"), 10);
		String h2Text = webDriver.findElement(By.tagName("h2")).getText();
		assertEquals("よくある質問", h2Text);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		// 検索キーワード入力欄が表示されるまで待機
		visibilityTimeout(By.name("keyword"), 10);

		//キーワード「東京ITスクール」を入力
		WebElement keywordInput = webDriver.findElement(By.name("keyword"));
		keywordInput.clear();
		keywordInput.sendKeys("東京ITスクール");

		//「検索」ボタン押下
		webDriver.findElement(By.cssSelector("input[type='submit'][value='検索']")).click();

		pageLoadTimeout(10);

		//URLに "keyword=東京ITスクール "が含まれているか判定
		String currentUrl = webDriver.getCurrentUrl();
		assertTrue(
				currentUrl.contains("keyword="),
				"URLにkeywordパラメータが存在しません: " + currentUrl);
		assertTrue(
				currentUrl.contains("東京ITスクール")
						|| currentUrl.contains("%E6%9D%B1%E4%BA%ACIT%E3%82%B9%E3%82%AF%E3%83%BC%E3%83%AB"),
				"URLに検索キーワードが付与されていません: " + currentUrl);

		//「データが登録されていません。」が表示されていないか判定
		List<WebElement> emptyRows = webDriver.findElements(
				By.xpath("//td[contains(@class,'dataTables_empty')]"));

		boolean isEmpty = emptyRows.stream()
				.anyMatch(e -> e.getText().contains("データが登録されていません。"));

		assertFalse(
				isEmpty,
				"検索結果が0件となり、『データが登録されていません。』が表示されています。");

		// 5. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {

		visibilityTimeout(By.name("keyword"), 10);

		WebElement keywordInput = webDriver.findElement(By.name("keyword"));
		String beforeClear = keywordInput.getAttribute("value");
		assertFalse(beforeClear.isEmpty(), "クリア前のキーワード欄が空欄です。");

		// クリアボタン押下
		webDriver.findElement(By.cssSelector("input[value='クリア']")).click();

		// value が "" になるまで待機
		new WebDriverWait(webDriver, Duration.ofSeconds(5))
				.until(ExpectedConditions.attributeToBe(By.name("keyword"), "value", ""));

		// 確認
		String afterClear = webDriver.findElement(By.name("keyword")).getAttribute("value");
		assertEquals("", afterClear, "クリア後もキーワード欄に値が残ってる。");

		getEvidence(new Object() {
		});
	}

}
