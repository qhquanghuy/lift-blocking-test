# TEST Blocking IO vs Lift Blocking to a thread pool

## TOOLS

https://github.com/philipgloyne/apachebench-for-multi-url

## Endpoint

- /blocking: simulate db blocking operation
- /lift-blocking: lift the blocking operation to Cache Thread Pool
- /normal: a normal endpoint

## TEST

```cli
ab -c 100 -v 4 -n 2000 -L blocking_urls.txt > blocking_results.txt

RESULT:

---

Server Software:
Server Hostname:        host.docker.internal
Server Port:            9000

Concurrency Level:      100
Time taken for tests:   164.254 seconds
Complete requests:      2000
Failed requests:        0
Write errors:           0
Total transferred:      1109145 bytes
HTML transferred:       445190 bytes
Requests per second:    12.18 [#/sec] (mean)
Time per request:       8212.681 [ms] (mean)
Time per request:       82.127 [ms] (mean, across all concurrent requests)
Transfer rate:          6.59 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    7   6.7      6      53
Processing:    12 7922 6898.3   5986   42086
Waiting:       11 7918 6898.5   5982   42080
Total:         13 7929 6898.3   5995   42090

Percentage of the requests served within a certain time (ms)
  50%   5995
  66%   8019
  75%  10041
  80%  12028
  90%  18024
  95%  22026
  98%  30039
  99%  34064
 100%  42090 (longest request)
```

```cli
ab -c 100 -v 4 -n 2000 -L lift_blocking_urls.txt > lift_blocking_results.txt

RESULT:


---


Server Software:
Server Hostname:        host.docker.internal
Server Port:            9000

Concurrency Level:      100
Time taken for tests:   22.250 seconds
Complete requests:      2000
Failed requests:        0
Write errors:           0
Total transferred:      1111800 bytes
HTML transferred:       450794 bytes
Requests per second:    89.89 [#/sec] (mean)
Time per request:       1112.493 [ms] (mean)
Time per request:       11.125 [ms] (mean, across all concurrent requests)
Transfer rate:          48.80 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    7   7.6      4      36
Processing:     0 1017 1002.6   1999    2041
Waiting:        0 1015 1002.4   1998    2035
Total:          1 1024 1003.1   2005    2068

Percentage of the requests served within a certain time (ms)
  50%   2005
  66%   2011
  75%   2017
  80%   2020
  90%   2031
  95%   2048
  98%   2055
  99%   2057
 100%   2068 (longest request)
```
