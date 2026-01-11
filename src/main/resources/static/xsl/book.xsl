<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8" />

  <xsl:template match="/book">
    <html>
      <head><title>Book</title></head>
      <body>
        <h1>Book</h1>
        <ul>
          <li><b>ID:</b> <xsl:value-of select="id"/></li>
          <li><b>Title:</b> <xsl:value-of select="title"/></li>
          <li><b>ISBN:</b> <xsl:value-of select="isbn"/></li>
          <li><b>Year:</b> <xsl:value-of select="publishedYear"/></li>
          <li>
            <b>Author:</b>
            <a>
              <xsl:attribute name="href">/api/authors/<xsl:value-of select="authorId"/></xsl:attribute>
              <xsl:value-of select="authorName"/>
            </a>
          </li>
        </ul>
        <p><a href="/api/books">Back to books</a></p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
