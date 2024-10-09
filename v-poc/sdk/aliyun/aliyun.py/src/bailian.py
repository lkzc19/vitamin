from openai import OpenAI
from dotenv import load_dotenv
import os
import json


load_dotenv()
api_key = os.getenv('API_KEY')


def ping():
    client = OpenAI(
        api_key=api_key,
        base_url="https://dashscope.aliyuncs.com/compatible-mode/v1",
    )
    completion = client.chat.completions.create(
        model="qwen-plus",
        messages=[
            {'role': 'system', 'content': 'You are a helpful assistant.'},
            {'role': 'user', 'content': '你是谁？'}
        ]
    )
    print(completion.choices[0].message.content)


if __name__ == '__main__':
    client = OpenAI(
        api_key=api_key,
        base_url="https://dashscope.aliyuncs.com/compatible-mode/v1"
    )

    text="""
    请作为MRO工业品领域的资深专家，执行以下任务：

    1. 仔细分析图片或者是文本内容
       - 集中注意力，全面审视图片或文本信息，里面包含了一个MRO工业品的订单详情，包含了品牌名称、品名、型号、数量、单位和货期。

    2. 精准提取工业品属性
       - 品牌名称：优先参考知识库${documents}中的定制品牌数据，确保品牌名称的准确无误。
       - 品名：清晰识别并记录下产品的具体名称。
       - 型号：细致区分不同版本或系列的型号，避免混淆。
       - 数量：明确标示的购买或库存数量，确保无遗漏。
       - 单位：记录数量的计量单位，如“个”、“件”、“套”等。
       - 货期：提取产品的交货时间或预计到货日期，注意时间格式的规范性。

    3. 直接输出结果
    如：西门子 电机启动器 G120C-2DP-B-V20 25台 10天
    ABB 断路器 S201-M10-C16 100个 7天
    请以文本输出：
    [
        {
            "品牌名称": "西门子",
            "品名": "电机启动器",
            "型号": "G120C-2DP-B-V20",
            "数量": 25,
            "单位": "台",
            "货期": "10天"
        },
        {
            "品牌名称": "ABB",
            "品名": "断路器",
            "型号": "S201-M10-C16",
            "数量": 100,
            "单位": "个",
            "货期": "7天"
        }
    ]

    4.补全未识别的信息
    在实际应用中，如果某些信息（如品牌名称、型号等）在图片中未明确显示或难以识别，需要根据上下文、行业标准或知识库来合理推测或补全这些信息，建议：
       - 如果品牌名称未识别，可以考虑使用常见的MRO工业品品牌进行替代，但应明确标记为“未知”或“待确认”。
       - 如果型号不清晰或缺失，可以参考近似供应商、制造商或参考类。

    5. 注意事项
       - 严格遵守MRO行业标准与产品分类体系。
       - 在识别过程中，注意处理多样化的描述方式，确保信息的全面性和准确性。
       - 如有必要，可引用知识库来源或行业标准文档作为参考依据。
    """

    completion = client.chat.completions.create(
        model="qwen-vl-max",
        messages=[
            {"role": "user", "content": [
                {"type": "image_url", "image_url": {"url": "https://ckmro.oss-cn-shanghai.aliyuncs.com/dev/ckmro/apps/test/2ab32a4a-7b0e-11ef-8cbb-047c16574f30"}},
                {"type": "text", "text": text}
            ]}
        ]
    )

    # print(completion.model_dump())
    # print(completion.model_dump()['choices'][0]['message']['content'])

    str = completion.model_dump()['choices'][0]['message']['content']
    list_of_dicts = json.loads(str)
    print(list_of_dicts[0]['品牌名称'])
