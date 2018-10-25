# -*- coding: utf-8 -*-
import scrapy

"""
测试使用start_requests发送post请求
"""


class HttpbinSpider(scrapy.Spider):
    name = 'httpbin'
    allowed_domains = ['httpbin.org']
    start_urls = ['http://httpbin.org/']

    def start_requests(self):
        yield scrapy.Request(url="http://httpbin.org/post", method='POST', callback=self.parse_post)

    def parse(self, response):
        pass

    def parse_post(self, response):
        print('hello', response.status)
