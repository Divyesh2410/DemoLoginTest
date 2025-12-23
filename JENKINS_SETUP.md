# Jenkins Setup Guide for LoginTestDemo

## Step-by-Step Jenkins Pipeline Setup

### Step 1: Install Required Jenkins Plugins

1. Go to **http://localhost:8080/**
2. Login to Jenkins
3. Click **Manage Jenkins** → **Manage Plugins**
4. Go to **Available** tab and install:
   - ✅ **Pipeline**
   - ✅ **HTML Publisher Plugin**
   - ✅ **TestNG Results Plugin** (if available)
   - ✅ **Git Plugin** (usually pre-installed)
   - ✅ **Maven Integration Plugin**

5. Click **Install without restart** or **Download now and install after restart**
6. Restart Jenkins if prompted

### Step 2: Configure Global Tools

1. Go to **Manage Jenkins** → **Global Tool Configuration**

2. **Configure JDK:**
   - Scroll to **JDK** section
   - Click **Add JDK**
   - Name: `JDK11`
   - JAVA_HOME: `C:\Program Files\Java\jdk-11` (or your JDK 11 path)
   - Or check **Install automatically** and select version 11

3. **Configure Maven:**
   - Scroll to **Maven** section
   - Click **Add Maven**
   - Name: `Maven`
   - MAVEN_HOME: `C:\Program Files\Apache\maven` (or your Maven path)
   - Or check **Install automatically** and select latest version

4. Click **Save**

### Step 3: Create Pipeline Job

1. Click **New Item** on Jenkins dashboard
2. Enter item name: `LoginTestDemo-Pipeline`
3. Select **Pipeline** → Click **OK**

### Step 4: Configure Pipeline

1. **General Section:**
   - ✅ Check **GitHub project** (optional)
   - Project url: `https://github.com/Divyesh2410/DemoLoginTest`

2. **Pipeline Section:**
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: `https://github.com/Divyesh2410/DemoLoginTest.git`
   - Credentials: (Leave empty for public repo, or add credentials if private)
   - Branch Specifier: `*/master` (or your branch name)
   - Script Path: `Jenkinsfile`

3. Click **Save**

### Step 5: Run the Pipeline

1. Click on your pipeline job: `LoginTestDemo-Pipeline`
2. Click **Build Now**
3. Watch the build progress in **Build History**
4. Click on the build number to see console output

### Step 6: View Test Reports

After build completes:

1. **TestNG Results:**
   - Click on build number
   - Look for **TestNG Results** link in left sidebar

2. **Extent Report:**
   - Click on build number
   - Look for **Extent Report** link in left sidebar

3. **TestNG HTML Report:**
   - Click on build number
   - Look for **TestNG Report** link in left sidebar

4. **Console Output:**
   - Click **Console Output** to see detailed logs

### Step 7: Configure Automatic Builds (Optional)

To trigger builds automatically on code changes:

1. Go to pipeline configuration
2. Under **Build Triggers**, check:
   - ✅ **Poll SCM** → Schedule: `H/5 * * * *` (every 5 minutes)
   - OR
   - ✅ **GitHub hook trigger for GITscm polling** (requires webhook setup)

### Troubleshooting

**Issue: Maven not found**
- Solution: Ensure Maven is configured in Global Tool Configuration

**Issue: JDK not found**
- Solution: Ensure JDK 11 is configured in Global Tool Configuration

**Issue: Tests fail with ChromeDriver**
- Solution: Ensure Chrome browser is installed on Jenkins server
- WebDriverManager should auto-download ChromeDriver

**Issue: Cannot checkout from Git**
- Solution: Check repository URL and branch name
- For private repos, add credentials in Jenkins

### Quick Test Command

To test if everything works locally before Jenkins:

```bash
mvn clean test
```

This should run all tests and generate reports in:
- `test-output/index.html` (TestNG Report)
- `reports/ExtentReport.html` (Extent Report)

