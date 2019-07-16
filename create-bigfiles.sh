#!/usr/bin/env bash

dd if=/dev/urandom of=shared/test.txt bs=1048576 count=1
dd if=/dev/urandom of=shared/file10G.txt bs=1048576 count=10000
dd if=/dev/urandom of=shared/file4G.txt bs=1048576 count=4000