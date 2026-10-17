DRAGON EGG FINDER — BUILD ON GITHUB (NO JAVA/GRADLE ON YOUR PC)

1. Sign in to GitHub and open https://github.com/new
2. Repository name: DragonEggFinder
3. Choose Public or Private, then click Create repository.
4. On the new repository page, choose Add file > Upload files.
5. Extract this ZIP on your PC first. Open the DragonEggFinder folder.
6. Upload EVERYTHING INSIDE that folder, including the hidden .github folder.
   If you cannot see .github in File Explorer, enable View > Show > Hidden items.
   Make sure build.gradle.kts, settings.gradle.kts, src, and .github are at the repository root.
7. Commit the upload.
8. Click Actions at the top of the GitHub repository. Open “Build DragonEggFinder”.
9. If it hasn't started automatically, click Run workflow.
10. Wait for the run to finish with a green check mark.
11. Open the successful workflow run, scroll to Artifacts, and download DragonEggFinder-JAR.
12. Extract that downloaded artifact ZIP. It contains DragonEggFinder-1.0.0.jar.
13. In BisectHosting, stop the server, open File Manager, open the plugins folder, and upload the JAR.
14. Start the server. Run /eggsearch start in-game as an operator.

IMPORTANT:
- Upload the JAR inside the GitHub artifact ZIP, not the source project ZIP.
- This plugin checks generated chunk positions inside each world's world border, one candidate position per tick. A very large border can take a very long time.
- Back up your world before running plugins. This project has not been runtime-tested on your exact server.
