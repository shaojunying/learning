# -*- coding: utf-8 -*-
import scrapy

from quotetutorial.items import QuoteItem


class QuotesSpider(scrapy.Spider):
    name = 'quotes'
    allowed_domains = ['quotes.toscrape.com']
    start_urls = ['http://quotes.toscrape.com/']

    def parse(self, response):
        quotes = response.css('.quote')
        for quote in quotes:
            # 此时将输出text类里面的内容
            # extract_first将会找出结果中第一个元素
            text = quote.css('.text::text').extract_first()
            author = quote.css('.author::text').extract_first()
            tags = quote.css('.tags .tag::text').extract()
            # 将提取的信息保存到item对象中
            item = QuoteItem()
            item['text'] = text
            item['author'] = author
            item['tags'] = tags
            # 将item返回出去
            yield item

            # 实现翻页
            next = response.css('.pager .next a::attr(href)').extract_first()
            url = response.urljoin(next)
            yield scrapy.Request(url=url, callback=self.parse)
