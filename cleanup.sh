git rm --cached `git ls-files -i --exclude-from=.gitignore`
git commit -m 'Removed all files that are in the .gitignore' 
git push origin master
