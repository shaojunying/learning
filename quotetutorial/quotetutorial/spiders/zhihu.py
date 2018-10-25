# -*- coding: utf-8 -*-
import scrapy

"""
此处主要测试使用from_crawler从setting文件中读取参数
"""


class ZhihuSpider(scrapy.Spider):
    name = 'zhihu'
    allowed_domains = ['zhihu.com']
    start_urls = ['http://zhihu.com/']

    def __init__(self, mongo_url, mongo_db, *args, **kwargs):
        super(ZhihuSpider, self).__init__(*args, **kwargs)
        self.mongo_url=mongo_url
        self.mongo_db=mongo_db

    @classmethod
    def from_crawler(cls, crawler, *args, **kwargs):
        return cls(
            mongo_url=crawler.settings.get('MONGO_URL'),
            mongo_db=crawler.settings.get('MONGO_DB')
        )

    def parse(self, response):
        pass
