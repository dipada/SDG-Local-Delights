
Get-ChildItem -Directory | Where-Object { $_.Name -notlike ".*" } | ForEach-Object {
    Write-Host "Installing $($_.Name) ..."
    Push-Location $_.FullName
    Start-Job -ScriptBlock {
        & mvn clean > $null
        & mvn install > $null
        Write-Host "  ... $($_.Name) correctly installed"
    }
    Pop-Location
}

# Aspetta che tutti i processi in background (jobs) siano completati
Get-Job | Wait-Job

# Stampa "Done" alla fine
Write-Host "Done"
