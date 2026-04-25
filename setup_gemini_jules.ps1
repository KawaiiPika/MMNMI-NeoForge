# Setup Gemini CLI and Jules Extension
# This script sets up a local Node.js environment and installs the necessary tools.

$projectRoot = Get-Location
$nodePath = Join-Path $projectRoot "tmp\node\node-v24.15.0-win-x64"

if (-not (Test-Path $nodePath)) {
    Write-Host "Node.js not found in tmp/node. Please ensure it is extracted correctly." -ForegroundColor Red
    return
}

# Add Node.js to the current session path
$env:PATH = "$nodePath;$env:PATH"

Write-Host "Verifying Node.js and npm..." -ForegroundColor Cyan
node --version
npm.cmd --version

# Install Gemini CLI if missing
Write-Host "Checking Gemini CLI..." -ForegroundColor Cyan
if (-not (Get-Command gemini.cmd -ErrorAction SilentlyContinue)) {
    Write-Host "Installing Gemini CLI..." -ForegroundColor Yellow
    npm.cmd install -g @google/gemini-cli
} else {
    Write-Host "Gemini CLI already installed." -ForegroundColor Green
}

# Install Jules Extension
Write-Host "Installing/Updating Jules Extension..." -ForegroundColor Cyan
gemini.cmd extensions install https://github.com/gemini-cli-extensions/jules --auto-update --consent

# Setup NotebookLM MCP
Write-Host "Setting up NotebookLM MCP..." -ForegroundColor Cyan
if (-not (Get-Command nlm -ErrorAction SilentlyContinue)) {
    Write-Host "Installing notebooklm-mcp-cli..." -ForegroundColor Yellow
    pip install notebooklm-mcp-cli
}

Write-Host "Setup Complete!" -ForegroundColor Green
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Run '.\gemini.cmd login' to authenticate Gemini CLI."
Write-Host "2. Run 'nlm login' to authenticate NotebookLM."
Write-Host "3. Start giving tasks to Jules with '/jules'!"
Write-Host "4. Use '/notebook' to interact with NotebookLM!"
