import { useState } from 'react';
import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
import '../assets/css/postReport.css'; // 스타일 공통 재사용

const CategoryPostList = () => {

    const [selectedCategory, setSelectedCategory] = useState('1'); // 기본 카테고리 ID
    const [category, setCategory] = useState({
      year: [],
      month: [],
      day: []
    });

    const categoryData = {
        1 : {name: ''},
    };




    return (
        <div className="dashboard-section">
            <h3>📊 카테고리별 게시글 수</h3>
            <p>년도, 월, 일 기준으로 카테고리별 게시글 수 통계를 확인할 수 있습니다.</p>

            <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)} style={{ margin: '20px 0' }}>
                {Object.entries(categoryData).map(([id, cat]) => (
                    <option key={id} value={id}>
                        {cat.name}
                    </option>
                ))}
            </select>

            <h4>📅 연도별</h4>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={category.year}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="count" fill="#8884d8" />
                </BarChart>
            </ResponsiveContainer>

            <h4>📆 월별</h4>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={category.month}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="count" fill="#82ca9d" />
                </BarChart>
            </ResponsiveContainer>

            <h4>🗓️ 일별</h4>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={category.day}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" interval={2} />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="count" fill="#ffc658" />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};

export default CategoryPostList;
