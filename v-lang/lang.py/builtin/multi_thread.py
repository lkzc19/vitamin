
from concurrent import futures
from concurrent.futures import ThreadPoolExecutor
import time

from tqdm import tqdm
import random


bar_format = '{l_bar}{bar} [{n_fmt}/{total_fmt}] {percentage:3.0f}%'


def delay(n):
    random_int = random.randint(a=1, b=10)
    time.sleep(random_int)


if __name__ == '__main__':
    start_time = time.time()

    tasks, results = [], []
    with ThreadPoolExecutor(max_workers=100) as executor:
        for it in range(1000):
            tasks.append(executor.submit(delay, it))
        for task in tqdm(futures.as_completed(tasks), total=len(tasks), bar_format=bar_format):
            results.append(task.result())

    end_time = time.time()
    elapsed_time = end_time - start_time
    print(f"耗时：{elapsed_time} 秒")