import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SampleTest {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;

    public static void handleAccordion(By locator, String attributeName) {
        WebElement element = driver.findElement(locator);

        String state = element.getAttribute(attributeName);

        // Common attributes: aria-expanded OR class
        if (state != null && (state.equals("false") || state.contains("collapsed"))) {
            element.click();
            System.out.println("Accordion was collapsed → Now expanded");
        } else {
            System.out.println("Accordion already expanded → No action taken");
        }
    }

    public static void clickMenu(String menu) throws InterruptedException {
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='" + menu + "']"))
        );
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center', behavior: 'smooth'});", element);
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        Thread.sleep(500);
    }

    public static void clickMainMenu(String menuText) throws InterruptedException {
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='" + menuText + "']"))
        );
        // Scroll to element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
        Thread.sleep(500);

        // Try to click using JavaScript if normal click fails
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            System.out.println("Normal click failed, trying JavaScript click for: " + menuText);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        Thread.sleep(500);
    }

    public static void selectReactDropdown(String inputId, String value) throws InterruptedException {
        try {
            WebElement dropdownInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(inputId)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdownInput);
            Thread.sleep(500);

            // Try to click and send keys
            try {
                dropdownInput.click();
                Thread.sleep(500);
                dropdownInput.clear();
                dropdownInput.sendKeys(value);
                Thread.sleep(500);
                dropdownInput.sendKeys(Keys.ENTER);
            } catch (Exception e) {
                // Fallback using JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownInput);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", dropdownInput, value);
                Thread.sleep(500);
                dropdownInput.sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            System.out.println("Error selecting dropdown value: " + value);
        }
    }

    public static void auto() throws InterruptedException {
        clickMenu("Auto Complete");
        WebElement multi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("autoCompleteMultipleInput")));
        multi.sendKeys("Red");
        Thread.sleep(500);
        multi.sendKeys(Keys.ENTER);
        Thread.sleep(500);
        WebElement single = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("autoCompleteSingleInput")));
        single.sendKeys("Blue");
        Thread.sleep(500);
        single.sendKeys(Keys.ENTER);
        Thread.sleep(500);
    }

    public static void auto1() throws InterruptedException {
        clickMenu("Date Picker");
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("datePickerMonthYearInput")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        wait.until(ExpectedConditions.elementToBeClickable(input));
        input.click();
        Thread.sleep(500);
        WebElement date = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'react-datepicker__day--015') and not(contains(@class,'--outside-month'))]")));
        date.click();
        Thread.sleep(500);
    }

    public static void auto2() throws InterruptedException {
        clickMenu("Slider");
        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='range']")));
        actions.dragAndDropBy(slider, 50, 0).perform();
        Thread.sleep(500);
    }

    public static void auto3() throws InterruptedException {
        clickMenu("Progress Bar");
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("startStopButton")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.attributeToBe(By.className("progress-bar"), "aria-valuenow", "100"));
        System.out.println("Progress Completed");
        Thread.sleep(500);
    }

    public static void auto4() throws InterruptedException {
        clickMenu("Tabs");
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("demo-tab-origin")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
        Thread.sleep(500);
        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("demo-tabpane-origin")));
        System.out.println("Tab Content: " + content.getText());
        Thread.sleep(500);
    }

    public static void auto5() throws InterruptedException {
        clickMenu("Tool Tips");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toolTipTextField")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        Thread.sleep(500);
        actions.moveToElement(input).perform();
        Thread.sleep(500);
        WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tooltip-inner")));
        System.out.println("Tooltip Text: " + tooltip.getText());
        Thread.sleep(500);
    }

    public static void auto6() throws InterruptedException {
        clickMenu("Menu");
        WebElement main = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Main Item 2']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", main);
        Thread.sleep(500);
        actions.moveToElement(main).perform();
        Thread.sleep(500);
        WebElement sub = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='SUB SUB LIST »']")));
        actions.moveToElement(sub).perform();
        Thread.sleep(500);
        sub.click();
        Thread.sleep(500);
    }

    public static void auto7() throws InterruptedException {
        clickMenu("Select Menu");
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("withOptGroup")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
        Thread.sleep(500);
        WebElement clickTarget = dropdown.findElement(By.xpath(".//div[contains(@class,'control')]"));
        clickTarget.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@id,'react-select') and text()='Group 2, option 1']")));
        option.click();
        Thread.sleep(500);
        WebElement selectOne = wait.until(ExpectedConditions.elementToBeClickable(By.id("selectOne")));
        selectOne.click();
        WebElement mrOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@id,'react-select') and text()='Mr.']")));
        mrOption.click();
        Thread.sleep(500);
        WebElement oldDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("oldSelectMenu")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", oldDropdown);
        new Select(oldDropdown).selectByVisibleText("Purple");
        Thread.sleep(500);
        WebElement multi = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cars")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", multi);
        Select select = new Select(multi);
        select.selectByVisibleText("Audi");
        Thread.sleep(500);
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void sortable() {
        driver.get("https://demoqa.com/sortable");

        WebElement item1 = driver.findElement(By.xpath("//div[text()='One']"));
        WebElement item4 = driver.findElement(By.xpath("//div[text()='Four']"));

        // Drag "One" and drop to position of "Four"
        actions.clickAndHold(item1)
                .moveToElement(item4)
                .release()
                .perform();
    }

    public static void selectable() {
        driver.get("https://demoqa.com/selectable");

        List<WebElement> items = driver.findElements(By.cssSelector("#verticalListContainer li"));

        // Select multiple items (Ctrl + Click)
        actions.keyDown(Keys.CONTROL)
                .click(items.get(0))
                .click(items.get(2))
                .click(items.get(3))
                .keyUp(Keys.CONTROL)
                .perform();
    }

    public static void resizable() {
        driver.get("https://demoqa.com/resizable");

        WebElement resizeHandle = driver.findElement(By.xpath("//div[@class='react-resizable-handle react-resizable-handle-se']"));

        // Resize box
        actions.clickAndHold(resizeHandle)
                .moveByOffset(100, 50)
                .release()
                .perform();
    }

    public static void droppable() {
        driver.get("https://demoqa.com/droppable");

        WebElement drag = driver.findElement(By.id("draggable"));
        WebElement drop = driver.findElement(By.id("droppable"));

        actions.dragAndDrop(drag, drop).perform();

        // Validation
        String text = drop.getText();
        System.out.println("Drop Result: " + text);
    }

    public static void draggable() {
        driver.get("https://demoqa.com/dragabble");

        WebElement dragBox = driver.findElement(By.id("dragBox"));

        actions.clickAndHold(dragBox)
                .moveByOffset(150, 100)
                .release()
                .perform();
    }

    public static void main(String[] args) throws InterruptedException {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);  // ✅ Actions initialized here after driver creation

        driver.get("https://demoqa.com/text-box");
        driver.manage().window().maximize();

        // Text Box
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Text Box']"))).click();
        driver.findElement(By.xpath("//input[@id = 'userName']")).sendKeys("Sam Singh");
        Thread.sleep(500);
        System.out.println("username enter Successfully");
        driver.findElement(By.xpath("//input[@id = 'userEmail']")).sendKeys("Sam@mailsac.com");
        Thread.sleep(500);
        System.out.println("email enter Successfully");
        driver.findElement(By.xpath("//textarea[@id = 'currentAddress']")).sendKeys("Pralhad Nagar Ahmedabad,Gujarat,India");
        Thread.sleep(500);
        System.out.println("Current address enter successfully ");
        driver.findElement(By.xpath("//textarea[@id ='permanentAddress']")).sendKeys("Same As Current");
        Thread.sleep(500);
        System.out.println("permanent address write Successfully");
        driver.findElement(By.xpath("//button[@id ='submit']")).click();
        Thread.sleep(500);
        String info = driver.findElement(By.xpath("//div[@id = 'output']")).getText();
        System.out.println("submit Successfully, here is the output :");
        System.out.println(info);
        Thread.sleep(500);

        // Checkbox
        WebElement checkBoxMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Check Box']")));
        checkBoxMenu.click();
        Thread.sleep(500);
        System.out.println("Navigated to Check Box successfully");
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[@role = 'checkbox']")).click();
        Thread.sleep(500);

        boolean isNoteDisplayed = driver.findElement(By.xpath("//span[text() = 'You have selected :']")).isDisplayed();

        if (isNoteDisplayed) {
            System.out.println("Checkbox clicked successfully");
        } else {
            System.out.println("checkbox is not clicked successfully");
        }
        Thread.sleep(500);

        // Radio Button
        WebElement rbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()= 'Radio Button']")));
        rbutton.click();
        Thread.sleep(500);
        System.out.println("Navigate to radio button");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'yesRadio']")).click();
        Thread.sleep(500);
        boolean isYes = driver.findElement(By.xpath("//p[contains(text() , 'You have selected')]")).isDisplayed();
        if (isYes) {
            System.out.println("You have selected Yes option");
        } else {
            System.out.println("You have selected Impressive option");
        }
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'impressiveRadio']")).click();
        Thread.sleep(500);
        boolean isYes1 = driver.findElement(By.xpath("//label[contains(text() , 'Impressive')]")).isDisplayed();
        if (isYes1) {
            System.out.println("You choose impressive option");
        } else {
            System.out.println("You have selected Yes option");
        }
        Thread.sleep(500);

        // Web Tables
        WebElement Wtable = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Web Tables']")));
        Wtable.click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//button[text() = 'Add']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'firstName']")).sendKeys("Samm");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'lastName']")).sendKeys("Singh");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'userEmail']")).sendKeys("sam@gmail.com");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'age']")).sendKeys("22");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'salary']")).sendKeys("30000");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id = 'department']")).sendKeys("Dev Ops");
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[text() = 'Submit']")).click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//input[@id = 'searchBox']")).sendKeys("Sam");

        boolean result1 = driver.findElements(By.xpath("//td[text() = 'Sam']")).size() > 0;

        if (result1) {
            System.out.println("Record Found");
        } else {
            System.out.println("No Record Found");
        }
        Thread.sleep(500);
        WebElement dropdown = driver.findElement(By.xpath("//select[@class ='form-control']"));
        Select select = new Select(dropdown);
        Thread.sleep(500);
        select.selectByValue("40");

        // Buttons
        WebElement Buttons = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Buttons']")));
        Buttons.click();
        Thread.sleep(500);
        WebElement element = driver.findElement(By.xpath("//button[text() = 'Double Click Me']"));
        Thread.sleep(500);
        actions.doubleClick(element).perform();  // ✅ Using class-level actions variable
        Thread.sleep(500);
        boolean doub = driver.findElement(By.xpath("//p[@id = 'doubleClickMessage']")).isDisplayed();
        if (doub) {
            System.out.println("Double click successful");
        } else {
            System.out.println("Something is missing");
        }
        Thread.sleep(500);
        WebElement element1 = driver.findElement(By.xpath("//button[text() = 'Right Click Me']"));
        actions.contextClick(element1).perform();  // ✅ Using class-level actions variable
        Thread.sleep(500);
        boolean right = driver.findElement(By.xpath("//p[@id = 'rightClickMessage']")).isDisplayed();
        if (right) {
            System.out.println("Right click successful");
        } else {
            System.out.println("Something is missing");
        }
        Thread.sleep(500);
        WebElement element2 = driver.findElement(By.xpath("//button[text() = 'Click Me']"));
        actions.click(element2).perform();  // ✅ Using class-level actions variable
        Thread.sleep(500);
        boolean click = driver.findElement(By.xpath("//p[@id = 'dynamicClickMessage']")).isDisplayed();
        if (click) {
            System.out.println("click successful");
        } else {
            System.out.println("Something is missing");
        }
        Thread.sleep(500);

        // Links
        WebElement Link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Links']")));
        Link.click();
        String parentWindow = driver.getWindowHandle();

        String[] linkIds = {
                "created",
                "no-content",
                "moved",
                "bad-request",
                "unauthorized",
                "forbidden",
                "invalid-url"
        };

        for (String id : linkIds) {
            try {
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
                link.click();
                Thread.sleep(500);
                WebElement response = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Link')]")));
                System.out.println("Response: " + response.getText());
            } catch (Exception e) {
                System.out.println("Error clicking link: " + id);
            }
        }

        // Broken Links - Images
        WebElement images = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Broken Links - Images']")));
        images.click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//a[contains(text() , 'Click Here for Valid Link')]")).click();
        System.out.println("Navigate to the new page in the same tab with valid link");
        Thread.sleep(500);
        driver.navigate().back();
        Thread.sleep(500);

        driver.findElement(By.xpath("//a[contains(text() , 'Click Here for Broken Link')]")).click();
        System.out.println("Navigate to the new page in the same tab with broken link");
        Thread.sleep(500);
        driver.navigate().back();
        Thread.sleep(500);

        // Upload and Download
        WebElement uploaddownload = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Upload and Download']")));
        uploaddownload.click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//a[text() = 'Download']")).click();
        Thread.sleep(500);
        System.out.println("file downloaded successfully");

        WebElement upload = driver.findElement(By.xpath("//input[@id = 'uploadFile']"));
        upload.sendKeys("C:/Users/saurabh.wake/Pictures/Screenshots/Screenshot 2026-05-06 114614.png");
        Thread.sleep(500);
        System.out.println("file uploaded successfully");

        // Forms Section
        WebElement formsMain = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text() = 'Forms']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", formsMain);
        Thread.sleep(500);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(formsMain));
            formsMain.click();
        } catch (Exception e) {
            System.out.println("Normal click failed, using JavaScript click for Forms");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", formsMain);
        }
        Thread.sleep(500);

        WebElement form = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Practice Form']")));
        form.click();
        Thread.sleep(500);

        driver.findElement(By.id("firstName")).sendKeys("Saurabh");
        Thread.sleep(500);
        driver.findElement(By.id("lastName")).sendKeys("Wake");
        Thread.sleep(500);
        driver.findElement(By.id("userEmail")).sendKeys("demo@mailsac.com");
        Thread.sleep(500);

        WebElement genderRadio = driver.findElement(By.id("gender-radio-1"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderRadio);
        Thread.sleep(500);

        driver.findElement(By.id("userNumber")).sendKeys("7276234589");
        Thread.sleep(500);

        driver.findElement(By.id("dateOfBirthInput")).click();
        Thread.sleep(500);

        Select month = new Select(driver.findElement(By.className("react-datepicker__month-select")));
        month.selectByVisibleText("March");
        Thread.sleep(500);

        Select year = new Select(driver.findElement(By.className("react-datepicker__year-select")));
        year.selectByVisibleText("2024");
        Thread.sleep(500);

        driver.findElement(By.xpath("//div[contains(@class,'react-datepicker__day') and text()='18']")).click();
        Thread.sleep(500);

        WebElement drop = driver.findElement(By.id("subjectsInput"));
        drop.sendKeys("Maths");
        drop.sendKeys(Keys.ENTER);
        Thread.sleep(500);

        WebElement hobbyCheckbox = driver.findElement(By.id("hobbies-checkbox-1"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hobbyCheckbox);
        Thread.sleep(500);

        WebElement upload0 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id = 'uploadPicture']")));
        Thread.sleep(500);
        upload0.sendKeys("C:/Users/saurabh.wake/Pictures/Screenshots/Screenshot 2026-05-06 114614.png");
        Thread.sleep(500);

        WebElement text = driver.findElement(By.xpath("//textarea[@class = 'form-control']"));
        text.sendKeys("Pralhad Nagar, Ahmedabad, Gujarat");
        Thread.sleep(500);

        // Handle state dropdown with better error handling
        try {
            WebElement stateInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("react-select-3-input")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", stateInput);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateInput);
            Thread.sleep(500);
            stateInput.sendKeys("Rajasthan");
            Thread.sleep(500);
            stateInput.sendKeys(Keys.ENTER);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Error selecting state: " + e.getMessage());
        }

        // Handle city dropdown with better error handling
        try {
            WebElement cityInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("react-select-4-input")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", cityInput);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cityInput);
            Thread.sleep(500);
            cityInput.sendKeys("Jaipur");
            Thread.sleep(500);
            cityInput.sendKeys(Keys.ENTER);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Error selecting city: " + e.getMessage());
        }

        driver.findElement(By.id("submit")).click();
        Thread.sleep(500);
        actions.sendKeys(Keys.ESCAPE).perform();  // ✅ Using class-level actions variable
        Thread.sleep(500);

        // Alerts, Frame & Windows
        WebElement alertMain = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text() = 'Alerts, Frame & Windows']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", alertMain);
        Thread.sleep(500);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(alertMain));
            alertMain.click();
        } catch (Exception e) {
            System.out.println("Normal click failed, using JavaScript click for Alerts, Frame & Windows");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", alertMain);
        }
        Thread.sleep(500);

        WebElement alertframe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Browser Windows']")));
        alertframe.click();
        Thread.sleep(500);

        String parent = driver.getWindowHandle();

        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(500);
        for (String w : driver.getWindowHandles()) {
            if (!w.equals(parent)) {
                driver.switchTo().window(w);
                try {
                    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
                    String title = driver.getTitle();
                    System.out.println("New Tab Title: " + (title.isEmpty() ? "No Title" : title));
                } catch (TimeoutException e) {
                    System.out.println("New Tab loaded but title not available");
                }
                driver.close();
                break;
            }
        }
        driver.switchTo().window(parent);
        Thread.sleep(500);

        driver.findElement(By.id("windowButton")).click();
        Thread.sleep(500);
        for (String w : driver.getWindowHandles()) {
            if (!w.equals(parent)) {
                driver.switchTo().window(w);
                try {
                    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
                    String title = driver.getTitle();
                    System.out.println("New Window Title: " + (title.isEmpty() ? "No Title" : title));
                } catch (TimeoutException e) {
                    System.out.println("New Window loaded but title not available");
                }
                driver.close();
                break;
            }
        }
        driver.switchTo().window(parent);
        Thread.sleep(500);

        driver.findElement(By.id("messageWindowButton")).click();
        Thread.sleep(500);
        for (String w : driver.getWindowHandles()) {
            if (!w.equals(parent)) {
                driver.switchTo().window(w);
                try {
                    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
                    String title = driver.getTitle();
                    System.out.println("Message Window Title: " + (title.isEmpty() ? "Empty Page" : title));
                } catch (TimeoutException e) {
                    System.out.println("Message Window loaded (blank page)");
                }
                driver.close();
                break;
            }
        }
        driver.switchTo().window(parent);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // Alerts
        WebElement alerts = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Alerts']")));
        alerts.click();
        Thread.sleep(500);

        driver.findElement(By.id("alertButton")).click();
        Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
        Thread.sleep(500);
        System.out.println("Normal alert : " + alert1.getText());
        alert1.accept();
        Thread.sleep(500);

        driver.findElement(By.id("timerAlertButton")).click();
        Alert alert2 = wait.until(ExpectedConditions.alertIsPresent());
        Thread.sleep(500);
        System.out.println("Timer Alert : " + alert2.getText());
        alert2.accept();
        Thread.sleep(500);

        driver.findElement(By.id("confirmButton")).click();
        Alert alert3 = wait.until(ExpectedConditions.alertIsPresent());
        Thread.sleep(500);
        System.out.println("Confirm alert : " + alert3.getText());
        alert3.dismiss();
        Thread.sleep(500);

        WebElement pro = driver.findElement(By.xpath("//button[@id = 'promtButton']"));
        pro.click();
        Alert alert4 = wait.until(ExpectedConditions.alertIsPresent());
        Thread.sleep(500);
        System.out.println("Prompt Alert : " + alert4.getText());
        Thread.sleep(500);
        alert4.sendKeys("Saurabh");
        alert4.accept();
        Thread.sleep(500);

        // Modal Dialogs
        WebElement Modal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Modal Dialogs']")));
        Modal.click();
        Thread.sleep(500);

        driver.findElement(By.id("showSmallModal")).click();
        Thread.sleep(500);
        String res = driver.findElement(By.xpath("//div[@class ='modal-body']")).getText();
        System.out.println("Small Modal Response : " + res);
        driver.findElement(By.xpath("//button[@class = 'btn-close']")).click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//button[@id = 'showLargeModal']")).click();
        Thread.sleep(500);
        String res1 = driver.findElement(By.xpath("//div[@class ='modal-body']")).getText();
        System.out.println("Large Modal Response : " + res1);
        driver.findElement(By.xpath("//button[@class = 'btn-close']")).click();
        Thread.sleep(500);

        // Widgets Tests
        driver.get("https://demoqa.com/widgets");
        Thread.sleep(500);






        tearDown();
    }
}