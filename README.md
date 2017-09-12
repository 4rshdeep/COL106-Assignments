* Course work for COL106, both assignments and my implementations of what is taught in the lecture.
* Use `git config --global --unset http.proxy` to unset proxy
* Use `git config --global http.proxy http://<userid>:<passwd>@proxy62.iitd.ac.in:3128`
* Added some files with a particular extension unknowingly do `git rm -r '*.class'`  and then `echo '*.class' >> .gitignore` 
* scp file_location_on_local cs5160625@palasi.cse.iitd.ac.in:/location/to/directory
* scp cs5160625@palasi.cse.iitd.ac.in:/home/dual/cs5160625/location ./  -- saves to pwd in local

And finally i found 100% work for me (check on two same laptops with different version Ubuntu 16.04LTS and 17.04)
1. Open Terminal, enter
iwconfig
and note down the wl* number.
2. Download new driver. Download rock.new_btcoex from GIT and unzip it to the desktop for example.
3. Again in Terminal type and run:
cd Desktop/rtlwifi_new-rock.new_btcoex
make
sudo make install type your ubuntu password.
sudo modprobe -rv rtl8723be
sudo modprobe -v rtl8723be ant_sel=2
sudo ip link set wl* up use your wl* number.
sudo iw dev wl* scan same.
To make the settings permanent, type below command
echo "options rtl8723be ant_sel=2" | sudo tee /etc/modprobe.d/50-rtl8723be.conf
Note: After your OS (Kernel) update, you need to apply these settings again to get strong signal.

