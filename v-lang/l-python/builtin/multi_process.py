import os
import multiprocessing
from multiprocessing import Process, Pool
import time


def proc():
    time.sleep(3)
    print(
        '{} Already Endding >> {}'.format(
            multiprocessing.current_process().name,
            time.strftime('%H:%M:%S',time.localtime(time.time()))
        )
    )

if __name__ == '__main__':
    multiprocessing.freeze_support()

	# 创建容量为4的进程池
    pool = Pool(4)

    for i in range(10):
        pool.apply_async(proc)
    pool.close()
    # 阻塞主进程，等所有子进程运行完后再通过
    pool.join()
    print(
        '{} Already Endding >> {}'.format(
            multiprocessing.current_process().name,
            time.strftime('%H:%M:%S', time.localtime(time.time()))
        )
    )
