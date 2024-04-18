#!/bin/sh

# scull_load
# echo "module module loaded successfully."

oldpath=`pwd`
cd /lib/modules/5.15.150-yocto-standard/extra/

case "$1" in
    start)
        ./module_load hello
        ./module_load faulty

        ;;
    stop)
        ./module_unload hello
        ./module_unload faulty
        ;;
    *)
        echo "Usage: $0 {start|stop}"
        exit 1
        ;;
esac

cd $oldpath
