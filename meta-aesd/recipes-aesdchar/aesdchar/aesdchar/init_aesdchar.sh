#!/bin/sh

# scull_load
# echo "Scull module loaded successfully."

oldpath=`pwd`
cd /lib/modules/5.15.150-yocto-standard/extra/

case "$1" in
    start)
        ./aesdchar_load
        ;;
    stop)
        ./aesdchar_unload
        ;;
    *)
        echo "Usage: $0 {start|stop}"
        exit 1
        ;;
esac

cd $oldpath
