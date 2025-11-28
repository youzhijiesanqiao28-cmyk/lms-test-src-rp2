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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		visibilityTimeout(By.linkText("【研修関係】"), 10);

		webDriver.findElement(By.linkText("【研修関係】")).click();

		pageLoadTimeout(5);

		//「キャンセル料・途中退校について」が表示されるまで待機
		By resultSpan = By.xpath("//span[contains(text(),'キャンセル料・途中退校')]");
		visibilityTimeout(resultSpan, 10);

		//画面を検索結果までスクロール
		org.openqa.selenium.WebElement target = webDriver.findElement(resultSpan);
		((org.openqa.selenium.JavascriptExecutor) webDriver)
				.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", target);

		//テキスト 一致確認
		String resultText = target.getText();
		assertEquals("キャンセル料・途中退校について", resultText);

		//エビデンス保存
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() throws Exception {

		By question = By.xpath("//span[contains(text(),'キャンセル料・途中退校')]");
		visibilityTimeout(question, 10);

		webDriver.findElement(question).click();
		Thread.sleep(300);

		By answer = By.xpath("//*[contains(normalize-space(), '受講者の退職や解雇')]");
		visibilityTimeout(answer, 10);

		WebElement answerElement = webDriver.findElement(answer);

		((JavascriptExecutor) webDriver)
				.executeScript("arguments[0].scrollIntoView({behavior:'instant',block:'center'});",
						answerElement);

		Thread.sleep(200);

		assertTrue(answerElement.getText().contains("受講者の退職や解雇"));

		getEvidence(new Object() {
		});
	}

}
